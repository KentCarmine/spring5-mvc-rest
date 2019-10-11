package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.domain.Vendor;
import guru.springfamework.exceptions.ResourceNotFoundException;
import guru.springfamework.services.VendorService;
import guru.springfamework.services.VendorServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest extends AbstractRestControllerTest {

    @Mock
    private VendorService vendorService;

    @InjectMocks
    private VendorController vendorController;

    private MockMvc mockMvc;

    private Vendor vendor1;
    private Vendor vendor2;
    private VendorDTO vendorDto1;
    private VendorDTO vendorDto2;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();

        vendor1 = new Vendor();
        vendor1.setId(1L);
        vendor1.setName("Test 1");

        vendor2 = new Vendor();
        vendor2.setId(2L);
        vendor2.setName("Test 2");

        vendorDto1 = VendorMapper.INSTANCE.vendorToVendorDTO(vendor1);
        vendorDto1.setVendorUrl(VendorServiceImpl.getVendorUrl(vendor1.getId()));

        vendorDto2 = VendorMapper.INSTANCE.vendorToVendorDTO(vendor2);
        vendorDto2.setVendorUrl(VendorServiceImpl.getVendorUrl(vendor2.getId()));
    }

    @Test
    public void getAllVendors() throws Exception {
        List<VendorDTO> returnDtoList = Arrays.asList(vendorDto1, vendorDto2);

        when(vendorService.getAllVendors()).thenReturn(returnDtoList);

        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(returnDtoList.size())));
    }

    @Test
    public void testGetVendorById() throws Exception {
        when(vendorService.getVendorById(anyLong())).thenReturn(vendorDto1);

        mockMvc.perform(get(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDto1.getName())))
                .andExpect(jsonPath("$.vendorUrl", equalTo(vendorDto1.getVendorUrl())));
    }

    @Test
    public void testCreateNewVendor() throws Exception {
        VendorDTO toReturn = vendorDto1;

        VendorDTO toSave = new VendorDTO();
        toSave.setName(toReturn.getName());
        toSave.setVendorUrl(toReturn.getVendorUrl());

        when(vendorService.createNewVendor(any(VendorDTO.class))).thenReturn(toReturn);

        mockMvc.perform(post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(toSave)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(toReturn.getName())))
                .andExpect(jsonPath("$.vendorUrl", equalTo(toReturn.getVendorUrl())));
    }

    @Test
    public void testUpdateVendor() throws Exception {
        VendorDTO toReturn = vendorDto1;

        VendorDTO toSave = new VendorDTO();
        toSave.setName(toReturn.getName());
        toSave.setVendorUrl(toReturn.getVendorUrl());

        when(vendorService.saveVendorByDto(anyLong(), any(VendorDTO.class))).thenReturn(toReturn);

        mockMvc.perform(put(toSave.getVendorUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(toSave)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(toReturn.getName())))
                .andExpect(jsonPath("$.vendorUrl", equalTo(toReturn.getVendorUrl())));
    }

    @Test
    public void testPatchVendor() throws Exception {
        when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(vendorDto1);

        mockMvc.perform(patch(vendorDto1.getVendorUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDto1.getName())));
    }

    @Test
    public void testDeleteVendor() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_URL + "/1"))
                .andExpect(status().isOk());

        verify(vendorService).deleteVendor(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {
        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(VendorController.BASE_URL + "/555")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}