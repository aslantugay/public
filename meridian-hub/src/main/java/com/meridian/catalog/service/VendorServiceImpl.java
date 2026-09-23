package com.meridian.catalog.service;

import com.meridian.catalog.VendorRepository;
import com.meridian.catalog.domain.Vendor;
import com.meridian.integration.http.FetchedResource;
import com.meridian.integration.http.HttpGateway;
import com.meridian.integration.storage.BlobStore;
import com.meridian.platform.security.AbstractTenantService;
import com.meridian.platform.security.CryptoService;
import com.meridian.platform.security.CurrentUserService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class VendorServiceImpl extends AbstractTenantService implements VendorService {

    private final VendorRepository vendorRepository;
    private final HttpGateway httpGateway;
    private final BlobStore blobStore;
    private final CryptoService cryptoService;

    public VendorServiceImpl(CurrentUserService currentUserService,
                             VendorRepository vendorRepository,
                             HttpGateway httpGateway,
                             BlobStore blobStore,
                             CryptoService cryptoService) {
        super(currentUserService);
        this.vendorRepository = vendorRepository;
        this.httpGateway = httpGateway;
        this.blobStore = blobStore;
        this.cryptoService = cryptoService;
    }

    @Override
    public Vendor create(String name, String contactEmail, String bankDetails) {
        Vendor vendor = new Vendor();
        vendor.setTenantId(currentTenantId());
        vendor.setName(name);
        vendor.setContactEmail(contactEmail);
        if (bankDetails != null) {
            vendor.setBankDetailsEnc(cryptoService.encrypt(bankDetails));
        }
        return vendorRepository.save(vendor);
    }

    @Override
    public Vendor get(Long vendorId) {
        return vendorRepository.findById(vendorId).orElse(null);
    }

    @Override
    public List<Vendor> listForTenant(Long tenantId) {
        return vendorRepository.findByTenantId(tenantId);
    }

    @Override
    public String importLogo(Long vendorId, String url) {
        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow();
        FetchedResource resource = httpGateway.get(url);
        String key = "vendors/" + vendorId + "/logo";
        blobStore.store(key, resource.getBody());
        vendor.setLogoKey(key);
        vendorRepository.save(vendor);
        return key;
    }
}
