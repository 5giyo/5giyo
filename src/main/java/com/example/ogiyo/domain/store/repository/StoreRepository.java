package com.example.ogiyo.domain.store.repository;

import com.example.ogiyo.domain.store.entity.Store;
import com.example.ogiyo.domain.store.dto.response.GetStoresResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import static com.example.ogiyo.domain.store.entity.Store.*;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
//    @Query("SELECT s FROM Store s JOIN FETCH Menu m on m.store = s WHERE s.storeId = :storeId")
//    Optional<Store> findById(@Param("storeId") Long storeId);

//    @Query("SELECT s FROM Store s JOIN FETCH s.menus m ON m.store = s WHERE s.storeName LIKE CONCAT('%', :name, '%') OR m.menuName LIKE CONCAT('%', :name, '%')")
//    Optional<Store> findAllByName(@Param("name") String name);

    // exact name 으로 find -> Like 로 수정?
    List<Store> findAllByStatusNot(Status status);
    List<Store> findByStoreNameAndStatusNot(String storeName, Store.Status status);

    default Store findByIdOrElseThrow(Long storeId) {
        return findById(storeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store Not Found"));
    }

    default List<GetStoresResponseDto> findAllToDto() {
        return findAllByStatusNot(Status.PERMANENTLY_CLOSED).stream()
                .map(GetStoresResponseDto::new)
                .toList();
    }

    default List<GetStoresResponseDto> findByStoreNameToDto(String storeName) {
        return findByStoreNameAndStatusNot(storeName, Status.PERMANENTLY_CLOSED).stream()
                .map(GetStoresResponseDto::new)
                .toList();
    }
}
