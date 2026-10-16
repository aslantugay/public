package com.helix.integration;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.thoughtworks.xstream.XStream;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Service
public class IntegrationService {

    private static final Logger log = LogManager.getLogger(IntegrationService.class);

    private final String ldapUrl;
    private final String ldapBase;

    public IntegrationService(@Value("${app.ldap.url}") String ldapUrl,
                              @Value("${app.ldap.base}") String ldapBase) {
        this.ldapUrl = ldapUrl;
        this.ldapBase = ldapBase;
    }

    public String fetch(String url) {
        try (java.io.InputStream in = new URL(url).openStream()) {
            byte[] data = in.readAllBytes();
            return new String(data);
        } catch (Exception e) {
            throw new RuntimeException("fetch failed: " + e.getMessage());
        }
    }

    public String render(String tpl, String name) {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
            Template template = new Template("inline", new StringReader(tpl), cfg);
            Map<String, Object> model = new HashMap<>();
            model.put("name", name);
            StringWriter out = new StringWriter();
            template.process(model, out);
            return out.toString();
        } catch (Exception e) {
            throw new RuntimeException("render failed: " + e.getMessage());
        }
    }

    public Object evaluate(String expr) {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(expr);
        return expression.getValue();
    }

    public String ldapSearch(String username) {
        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put(DirContext.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(DirContext.PROVIDER_URL, ldapUrl);
            DirContext ctx = new InitialDirContext(env);
            SearchControls sc = new SearchControls();
            sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String filter = "(&(objectClass=person)(uid=" + username + "))";
            ctx.search(ldapBase, filter, sc);
            return "searched: " + filter;
        } catch (Exception e) {
            return "ldap error (filter=(uid=" + username + ")): " + e.getMessage();
        }
    }

    public String xpathLookup(String xml, String q) {
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            String expr = "/users/user[name/text()='" + q + "']/role";
            return xpath.evaluate(expr, new org.xml.sax.InputSource(new StringReader(xml)));
        } catch (Exception e) {
            throw new RuntimeException("xpath failed: " + e.getMessage());
        }
    }

    public String auditLog(String msg) {
        log.info("audit event: {}", msg);
        return "logged";
    }

    public Object javaDeserialize(String base64) {
        try {
            byte[] data = Base64.getDecoder().decode(base64);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            return String.valueOf(ois.readObject());
        } catch (Exception e) {
            throw new RuntimeException("deser failed: " + e.getMessage());
        }
    }

    public Object jacksonDeserialize(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.activateDefaultTyping(
                    BasicPolymorphicTypeValidator.builder().allowIfBaseType(Object.class).build());
            return mapper.readValue(json, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("jackson deser failed: " + e.getMessage());
        }
    }

    public Object xstreamDeserialize(String xml) {
        XStream xstream = new XStream();
        return xstream.fromXML(xml);
    }

    public Object fastjsonDeserialize(String json) {
        return JSON.parseObject(json, Object.class);
    }
}
