package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.services.VendorService;
import guru.springfamework.services.VendorServiceImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VendorMapperImplTest {
    private static final Long ID = 1L;
    private static final String NAME = "Test Vendor";
    private static final String URL = VendorServiceImpl.getVendorUrl(ID);

    private VendorMapper vendorMapper;

    @Before
    public void setUp() throws Exception {
        vendorMapper = VendorMapper.INSTANCE;
    }

    @Test
    public void vendorToVendorDTO() {
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        VendorDTO resultDto = vendorMapper.vendorToVendorDTO(vendor);

        assertEquals(vendor.getName(), resultDto.getName());
    }

    @Test
    public void vendorDTOToVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor result = vendorMapper.vendorDTOToVendor(vendorDTO);

        assertEquals(vendorDTO.getName(), result.getName());
    }
}