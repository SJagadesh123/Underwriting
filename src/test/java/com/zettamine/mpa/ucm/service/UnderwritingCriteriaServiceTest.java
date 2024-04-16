//package com.zettamine.mpa.ucm.service;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;
//
//import com.zettamine.mpa.ucm.dto.UnderwritingCriteriaDto;
//import com.zettamine.mpa.ucm.entities.UnderwritingCriteria;
//import com.zettamine.mpa.ucm.entities.UnderwritingCriteriaLoanProduct;
//import com.zettamine.mpa.ucm.exception.DuplicationException;
//import com.zettamine.mpa.ucm.exception.ResourceNotFoundException;
//import com.zettamine.mpa.ucm.mapper.UnderwritingCriteriaMapper;
//import com.zettamine.mpa.ucm.repository.SearchCriteriaRepository;
//import com.zettamine.mpa.ucm.repository.UnderwritingCriteriaLoanProductRepository;
//import com.zettamine.mpa.ucm.repository.UnderwritingCriteriaRepository;
//import com.zettamine.mpa.ucm.service.clients.LoanProductFeignClient;
//import com.zettamine.mpa.ucm.utility.StringUtils;
//
//class UnderwritingCriteriaServiceImplTest {
//
//    @Mock
//    private UnderwritingCriteriaRepository underwritingCriteriaRepository;
//    
//    @Mock
//    private UnderwritingCriteriaLoanProductRepository criteriaLoanProductRepository;
//    
//    @Mock
//    private LoanProductFeignClient loanProductFeignClient;
//    
//    @Mock
//    private SearchCriteriaRepository searchCriteriaRepository;
//
//    @InjectMocks
//    private UnderwritingCriteriaServiceImpl underwritingCriteriaService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testSave_Success() throws Exception {
//        // Given
//        UnderwritingCriteriaDto dto = new UnderwritingCriteriaDto();
//        dto.setCriteriaName("Test Criteria");
//        when(underwritingCriteriaRepository.findByCriteriaName(any())).thenReturn(Optional.empty());
//        
//        // When
//        underwritingCriteriaService.save(dto);
//        
//        // Then
//        verify(underwritingCriteriaRepository, times(1)).save(any(UnderwritingCriteria.class));
//    }
//
//    @Test
//    void testSave_DuplicateCriteria_ThrowsDuplicationException() {
//        // Given
//        UnderwritingCriteriaDto dto = new UnderwritingCriteriaDto();
//        dto.setCriteriaName("Existing Criteria");
//        when(underwritingCriteriaRepository.findByCriteriaName(any())).thenReturn(Optional.of(new UnderwritingCriteria()));
//        
//        // When/Then
//        assertThrows(DuplicationException.class, () -> underwritingCriteriaService.save(dto));
//    }
//
//    @Test
//    void testUpdate_Success() throws Exception {
//        // Given
//        Long id = 1L;
//        UnderwritingCriteriaDto dto = new UnderwritingCriteriaDto();
//        dto.setCriteriaName("Updated Criteria");
//        UnderwritingCriteria existingCriteria = new UnderwritingCriteria();
//        existingCriteria.setCriteriaId(id);
//        when(underwritingCriteriaRepository.findById(id)).thenReturn(Optional.of(existingCriteria));
//        when(underwritingCriteriaRepository.findByCriteriaName(any())).thenReturn(Optional.empty());
//        
//        // When
//        underwritingCriteriaService.update(id, dto);
//        
//        // Then
//        verify(underwritingCriteriaRepository, times(1)).save(any(UnderwritingCriteria.class));
//    }
//
//    @Test
//    void testUpdate_DuplicateCriteria_ThrowsDuplicationException() {
//        // Given
//        Long id = 1L;
//        UnderwritingCriteriaDto dto = new UnderwritingCriteriaDto();
//        dto.setCriteriaName("Existing Criteria");
//        UnderwritingCriteria existingCriteria = new UnderwritingCriteria();
//        existingCriteria.setCriteriaId(id);
//        when(underwritingCriteriaRepository.findById(id)).thenReturn(Optional.of(existingCriteria));
//        when(underwritingCriteriaRepository.findByCriteriaName(any())).thenReturn(Optional.of(new UnderwritingCriteria()));
//        
//        // When/Then
//        assertThrows(DuplicationException.class, () -> underwritingCriteriaService.update(id, dto));
//    }
//
//    @Test
//    void testUpdate_NonExistentCriteria_ThrowsResourceNotFoundException() {
//        // Given
//        Long id = 1L;
//        UnderwritingCriteriaDto dto = new UnderwritingCriteriaDto();
//        dto.setCriteriaName("Updated Criteria");
//        when(underwritingCriteriaRepository.findById(id)).thenReturn(Optional.empty());
//        
//        // When/Then
//        assertThrows(ResourceNotFoundException.class, () -> underwritingCriteriaService.update(id, dto));
//    }
//
//    @Test
//    void testGet_Success() {
//        // Given
//        Long id = 1L;
//        UnderwritingCriteria criteria = new UnderwritingCriteria();
//        criteria.setCriteriaId(id);
//        when(underwritingCriteriaRepository.findById(id)).thenReturn(Optional.of(criteria));
//        
//        // When
//        UnderwritingCriteriaDto result = underwritingCriteriaService.get(id);
//        
//        // Then
//        assertNotNull(result);
//        assertEquals(id, result.getCriteriaId());
//    }
//
//    @Test
//    void testGet_NonExistentCriteria_ThrowsResourceNotFoundException() {
//        // Given
//        Long id = 1L;
//        when(underwritingCriteriaRepository.findById(id)).thenReturn(Optional.empty());
//        
//        // When/Then
//        assertThrows(ResourceNotFoundException.class, () -> underwritingCriteriaService.get(id));
//    }
//
//    @Test
//    void testGetAll_Success() {
//        // Given
//        List<UnderwritingCriteria> criteriaList = new ArrayList<>();
//        criteriaList.add(new UnderwritingCriteria());
//        criteriaList.add(new UnderwritingCriteria());
//        when(underwritingCriteriaRepository.findAll()).thenReturn(criteriaList);
//        
//        // When
//        List<UnderwritingCriteriaDto> result = underwritingCriteriaService.getAll();
//        
//        // Then
//        assertNotNull(result);
//        assertFalse(result.isEmpty());
//        assertEquals(criteriaList.size(), result.size());
//    }
//
//    @Test
//    void testAddCriteriaToLoanProd_Success() {
//        // Given
//        List<String> criteriaNames = List.of("Criteria 1", "Criteria 2");
//        String loanProductName = "Test Loan Product";
//        when(loanProductFeignClient.getLoanProductIdByName(any())).thenReturn(ResponseEntity.ok(1));
//        when(underwritingCriteriaRepository.findByCriteriaName(any())).thenReturn(Optional.of(new UnderwritingCriteria()));
//        
//        // When
//        assertDoesNotThrow(() -> underwritingCriteriaService.addCriteriaToLoanProd(criteriaNames, loanProductName));
//        
//        // Then
//        verify(criteriaLoanProductRepository, times(criteriaNames.size())).save(any(UnderwritingCriteriaLoanProduct.class));
//    }
//
//    @Test
//    void testAddCriteriaToLoanProd_NonExistentLoanProduct_ThrowsResourceNotFoundException() {
//        // Given
//        List<String> criteriaNames = List.of("Criteria 1", "Criteria 2");
//        String loanProductName = "Non-existent Loan Product";
//        when(loanProductFeignClient.getLoanProductIdByName(any())).thenReturn(ResponseEntity.ok(null));
//        
//        // When/Then
//        assertThrows(ResourceNotFoundException.class, () -> underwritingCriteriaService.addCriteriaToLoanProd(criteriaNames, loanProductName));
//    }
//
//    @Test
//    void testRemoveCriteriaToLoanProd_Success() {
//        // Given
//        List<String> criteriaNames = List.of("Criteria 1", "Criteria 2");
//        String loanProductName = "Test Loan Product";
//        when(loanProductFeignClient.getLoanProductIdByName(any())).thenReturn(ResponseEntity.ok(1));
//        when(underwritingCriteriaRepository.findByCriteriaName(any())).thenReturn(Optional.of(new UnderwritingCriteria()));
//        
//        // When
//        assertDoesNotThrow(() -> underwritingCriteriaService.removeCriteriaToLoanProd(criteriaNames, loanProductName));
//        
//        // Then
//        verify(criteriaLoanProductRepository, times(criteriaNames.size())).delete(any(UnderwritingCriteriaLoanProduct.class));
//    }
//
//    @Test
//    void testRemoveCriteriaToLoanProd_NonExistentLoanProduct_ThrowsResourceNotFoundException() {
//        // Given
//        List<String> criteriaNames = List.of("Criteria 1", "Criteria 2");
//        String loanProductName = "Non-existent Loan Product";
//        when(loanProductFeignClient.getLoanProductIdByName(any())).thenReturn(ResponseEntity.ok(null));
//        
//        // When/Then
//        assertThrows(ResourceNotFoundException.class, () -> underwritingCriteriaService.removeCriteriaToLoanProd(criteriaNames, loanProductName));
//    }
//
//    @Test
//    void testGetByCriterias_Success() {
//        // Given
//        List<String> criteriaNames = List.of("Criteria 1", "Criteria 2");
//        Set<Integer> loanProductIds = Set.of(1, 2);
//        when(searchCriteriaRepository.getLoanProductByCriteria(criteriaNames)).thenReturn(loanProductIds);
//        
//        // When
//        Set<Integer> result = underwritingCriteriaService.getByCriterias(criteriaNames);
//        
//        // Then
//        assertNotNull(result);
//        assertEquals(loanProductIds, result);
//    }
//
//    @Test
//    void testGetAllCriteriaNames_Success() {
//        // Given
//        List<UnderwritingCriteria> criteriaList = new ArrayList<>();
//        criteriaList.add(new UnderwritingCriteria("Criteria 1"));
//        criteriaList.add(new UnderwritingCriteria("Criteria 2"));
//        when(underwritingCriteriaRepository.findAll()).thenReturn(criteriaList);
//        
//        // When
//        List<String> result = underwritingCriteriaService.getAllCriteriaNames();
//        
//        // Then
//        assertNotNull(result);
//        assertFalse(result.isEmpty());
//        assertEquals(criteriaList.size(), result.size());
//    }
//
//    @Test
//    void testGetByLoanId_Success() {
//        // Given
//        Integer loanId = 1;
//        List<UnderwritingCriteriaLoanProduct> criteriaLoanProducts = List.of(new UnderwritingCriteriaLoanProduct(), new UnderwritingCriteriaLoanProduct());
//        when(criteriaLoanProductRepository.findByProdId(loanId)).thenReturn(criteriaLoanProducts);
//        
//        // When
//        List<UnderwritingCriteriaDto> result = underwritingCriteriaService.getByLoanId(loanId);
//        
//        // Then
//        assertNotNull(result);
//        assertFalse(result.isEmpty());
//        assertEquals(criteriaLoanProducts.size(), result.size());
//    }
//}
