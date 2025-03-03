package com.example.ogiyo.store.repository;

import com.example.ogiyo.store.dto.response.GetStoresResponseDto;
import com.example.ogiyo.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
//    @Query("SELECT s FROM Store s JOIN FETCH Menu m on m.store = s WHERE s.storeId = :storeId")
//    Optional<Store> findById(@Param("storeId") Long storeId);

    List<Store> findByStoreNameAndStatusNot(String storeName, Store.Status status);

    default Store findByIdOrElseThrow(Long storeId) {
        return findById(storeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store Not Found"));
    }

    default List<GetStoresResponseDto> findByStoreNameToDto(String storeName) {
        return findByStoreNameAndStatusNot(storeName, Store.Status.PERMANENTLY_CLOSED).stream()
                .map(GetStoresResponseDto::new)
                .toList();
    }
}
