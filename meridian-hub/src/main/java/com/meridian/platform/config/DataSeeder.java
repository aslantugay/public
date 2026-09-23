package com.meridian.platform.config;

import com.meridian.automation.InviteCodeRepository;
import com.meridian.automation.NotificationTemplateRepository;
import com.meridian.automation.WebhookDeliveryRepository;
import com.meridian.automation.WebhookRepository;
import com.meridian.automation.domain.InviteCode;
import com.meridian.automation.domain.NotificationTemplate;
import com.meridian.automation.domain.Webhook;
import com.meridian.automation.domain.WebhookDelivery;
import com.meridian.docs.CommentRepository;
import com.meridian.docs.DocumentRepository;
import com.meridian.docs.domain.Comment;
import com.meridian.docs.domain.Document;
import com.meridian.iam.UserRepository;
import com.meridian.iam.domain.Role;
import com.meridian.iam.domain.User;
import com.meridian.platform.security.PasswordHasher;
import com.meridian.procurement.CreditAccountRepository;
import com.meridian.procurement.InvoiceRepository;
import com.meridian.procurement.domain.CreditAccount;
import com.meridian.procurement.domain.Invoice;
import com.meridian.tenancy.TenantRepository;
import com.meridian.tenancy.UnitRepository;
import com.meridian.tenancy.domain.Tenant;
import com.meridian.tenancy.domain.Unit;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final TenantRepository tenantRepository;
    private final UnitRepository unitRepository;
    private final UserRepository userRepository;
    private final CreditAccountRepository creditAccountRepository;
    private final DocumentRepository documentRepository;
    private final CommentRepository commentRepository;
    private final InvoiceRepository invoiceRepository;
    private final WebhookRepository webhookRepository;
    private final WebhookDeliveryRepository webhookDeliveryRepository;
    private final InviteCodeRepository inviteCodeRepository;
    private final NotificationTemplateRepository notificationTemplateRepository;
    private final PasswordHasher passwordHasher;

    public DataSeeder(TenantRepository tenantRepository,
                      UnitRepository unitRepository,
                      UserRepository userRepository,
                      CreditAccountRepository creditAccountRepository,
                      DocumentRepository documentRepository,
                      CommentRepository commentRepository,
                      InvoiceRepository invoiceRepository,
                      WebhookRepository webhookRepository,
                      WebhookDeliveryRepository webhookDeliveryRepository,
                      InviteCodeRepository inviteCodeRepository,
                      NotificationTemplateRepository notificationTemplateRepository,
                      PasswordHasher passwordHasher) {
        this.tenantRepository = tenantRepository;
        this.unitRepository = unitRepository;
        this.userRepository = userRepository;
        this.creditAccountRepository = creditAccountRepository;
        this.documentRepository = documentRepository;
        this.commentRepository = commentRepository;
        this.invoiceRepository = invoiceRepository;
        this.webhookRepository = webhookRepository;
        this.webhookDeliveryRepository = webhookDeliveryRepository;
        this.inviteCodeRepository = inviteCodeRepository;
        this.notificationTemplateRepository = notificationTemplateRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public void run(String... args) {
        Tenant acme = newTenant("Acme Holding", "1234567890", "billing@acme.example");
        Tenant globex = newTenant("Globex Corp", "9876543210", "billing@globex.example");

        creditAccount(acme, "5000.00");
        creditAccount(globex, "5000.00");

        Unit acmeFinance = newUnit(acme, "Finance", "CC-100");
        Unit acmeHr = newUnit(acme, "HR", "CC-200");
        Unit globexRnd = newUnit(globex, "R&D", "CC-900");

        newUser(acme, acmeFinance, "alice@acme.example", "Alice Admin", Role.TENANT_ADMIN, "alice123");
        newUser(acme, acmeHr, "bob@acme.example", "Bob Manager", Role.UNIT_MANAGER, "bob123");
        newUser(acme, acmeFinance, "dave@acme.example", "Dave Auditor", Role.AUDITOR, "dave123");
        newUser(globex, globexRnd, "carol@globex.example", "Carol Manager", Role.UNIT_MANAGER, "carol123");

        Document acmeDoc = newDocument(acme, acmeFinance, "Q3 Financials", "financials/q3.pdf", "CONFIDENTIAL");
        Document globexDoc = newDocument(globex, globexRnd, "Project Zeus Spec", "specs/zeus.pdf", "SECRET");

        newComment(acmeDoc, "alice@acme.example", "Uploaded the reviewed Q3 numbers.");
        newComment(globexDoc, "carol@globex.example", "First draft of the Zeus spec is ready.");

        newInvoice(acme, "INV-1001", "Initech", "4200.00");
        newInvoice(globex, "INV-2001", "Umbrella", "9100.00");

        // Automation dashboard sample data: a delivery log that quotes the shared
        // document token in its payload.
        Webhook hook = newWebhook(globex, "https://hooks.globex.example/inbound");
        newDelivery(globex, hook, "document.shared",
                "{\"event\":\"document.shared\",\"documentShareToken\":\""
                        + globexDoc.getShareToken() + "\",\"title\":\"Project Zeus Spec\"}");

        newInvite(acme, "WELCOME1", Role.TENANT_ADMIN);
        newTemplate(acme, "greeting", "'Hello ' + #name + ', welcome to Meridian.'");
    }

    private Tenant newTenant(String name, String tax, String email) {
        Tenant tenant = new Tenant();
        tenant.setName(name);
        tenant.setTaxNumber(tax);
        tenant.setBillingEmail(email);
        return tenantRepository.save(tenant);
    }

    private void creditAccount(Tenant tenant, String balance) {
        CreditAccount account = new CreditAccount();
        account.setTenantId(tenant.getId());
        account.setBalance(new BigDecimal(balance));
        creditAccountRepository.save(account);
    }

    private Unit newUnit(Tenant tenant, String name, String costCenter) {
        Unit unit = new Unit();
        unit.setTenant(tenant);
        unit.setName(name);
        unit.setCostCenter(costCenter);
        return unitRepository.save(unit);
    }

    private void newUser(Tenant tenant, Unit unit, String email, String name, Role role, String pwd) {
        User user = new User();
        user.setTenantId(tenant.getId());
        user.setUnitId(unit.getId());
        user.setEmail(email);
        user.setDisplayName(name);
        user.setRole(role);
        user.setPasswordHash(passwordHasher.hash(pwd));
        userRepository.save(user);
    }

    private Document newDocument(Tenant tenant, Unit unit, String title, String key, String classification) {
        Document document = new Document();
        document.setShareToken(UUID.randomUUID().toString());
        document.setTenantId(tenant.getId());
        document.setUnitId(unit.getId());
        document.setTitle(title);
        document.setStorageKey(key);
        document.setClassification(classification);
        document.setTag("fy2026");
        return documentRepository.save(document);
    }

    private void newComment(Document document, String authorEmail, String body) {
        Comment comment = new Comment();
        comment.setDocumentShareToken(document.getShareToken());
        comment.setDocumentTitle(document.getTitle());
        comment.setAuthorEmail(authorEmail);
        comment.setBody(body);
        commentRepository.save(comment);
    }

    private void newInvoice(Tenant tenant, String number, String customer, String amount) {
        Invoice invoice = new Invoice();
        invoice.setTenantId(tenant.getId());
        invoice.setInvoiceNumber(number);
        invoice.setCustomerName(customer);
        invoice.setAmount(new BigDecimal(amount));
        invoice.setMemoHtml("<p>Thank you for your business.</p>");
        invoiceRepository.save(invoice);
    }

    private Webhook newWebhook(Tenant tenant, String url) {
        Webhook webhook = new Webhook();
        webhook.setTenantId(tenant.getId());
        webhook.setTargetUrl(url);
        return webhookRepository.save(webhook);
    }

    private void newDelivery(Tenant tenant, Webhook hook, String event, String payload) {
        webhookDeliveryRepository.save(
                new WebhookDelivery(tenant.getId(), hook.getId(), event, payload, "200"));
    }

    private void newInvite(Tenant tenant, String code, Role role) {
        InviteCode invite = new InviteCode();
        invite.setTenantId(tenant.getId());
        invite.setCode(code);
        invite.setGrantedRole(role);
        inviteCodeRepository.save(invite);
    }

    private void newTemplate(Tenant tenant, String name, String body) {
        NotificationTemplate template = new NotificationTemplate();
        template.setTenantId(tenant.getId());
        template.setName(name);
        template.setBody(body);
        notificationTemplateRepository.save(template);
    }
}
