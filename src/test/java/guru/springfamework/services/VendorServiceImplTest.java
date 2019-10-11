package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.exceptions.ResourceNotFoundException;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class VendorServiceImplTest {

    @Mock
    private VendorRepository vendorRepository;

    private VendorMapper vendorMapper;

    private VendorService vendorService;

    private Vendor vendor1;
    private Vendor vendor2;
    private VendorDTO vendorDto1;
    private VendorDTO vendorDto2;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vendorMapper = VendorMapper.INSTANCE;
        vendorService = new VendorServiceImpl(vendorRepository, vendorMapper);

        vendor1 = new Vendor();
        vendor1.setId(1L);
        vendor1.setName("Test 1");

        vendor2 = new Vendor();
        vendor2.setId(2L);
        vendor2.setName("Test 2");

        vendorDto1 = vendorMapper.vendorToVendorDTO(vendor1);
        vendorDto1.setVendorUrl(VendorServiceImpl.getVendorUrl(vendor1.getId()));

        vendorDto2 = vendorMapper.vendorToVendorDTO(vendor2);
        vendorDto2.setVendorUrl(VendorServiceImpl.getVendorUrl(vendor2.getId()));
    }

    @Test
    public void testGetAllVendors() {
        List<Vendor> returnList = Arrays.asList(vendor1, vendor2);

        when(vendorRepository.findAll()).thenReturn(returnList);

        List<VendorDTO> vendorDtos = vendorService.getAllVendors();

        assertEquals(returnList.size(), vendorDtos.size());
    }

    @Test
    public void testGetVendorById() throws Exception {
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor1));

        VendorDTO vendorDTO = vendorService.getVendorById(1L);

        assertEquals(vendor1.getName(), vendorDTO.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetVendorByIdNotFound() throws Exception {
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.empty());

        vendorService.getVendorById(75L);

        verify(vendorRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testCreateNewVendor() throws Exception {
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor1);

        VendorDTO savedVendor = vendorService.createNewVendor(vendorDto1);

        assertEquals(vendor1.getName(), savedVendor.getName());
        assertEquals(VendorServiceImpl.getVendorUrl(vendor1.getId()), vendorDto1.getVendorUrl());
        verify(vendorRepository, times(1)).save(any(Vendor.class));
    }

    @Test
    public void testSaveVendorByDto() throws Exception {
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor1);

        VendorDTO savedVendor = vendorService.saveVendorByDto(vendor1.getId(), vendorDto1);

        assertEquals(vendor1.getName(), savedVendor.getName());
        assertEquals(VendorServiceImpl.getVendorUrl(vendor1.getId()), vendorDto1.getVendorUrl());
        verify(vendorRepository, times(1)).save(any(Vendor.class));
    }

    @Test
    public void patchVendor() throws Exception {
        VendorDTO renamedVendor = new VendorDTO();
        renamedVendor.setName("Bob");

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor1));
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor1);

        VendorDTO savedVendorDto = vendorService.patchVendor(vendor1.getId(), renamedVendor);

        verify(vendorRepository, times(1)).save(any(Vendor.class));
        verify(vendorRepository, times(1)).findById(anyLong());
        assertEquals(renamedVendor.getName(), savedVendorDto.getName());
        assertEquals(VendorServiceImpl.getVendorUrl(vendor1.getId()), savedVendorDto.getVendorUrl());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void patchVendorNotFound() throws Exception {
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.empty());

        vendorService.patchVendor(75L, vendorDto1);
    }

    @Test
    public void deleteVendor() throws Exception {
        vendorService.deleteVendor(vendor1.getId());

        verify(vendorRepository).deleteById(anyLong());
    }
}