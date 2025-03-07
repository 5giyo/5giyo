package com.example.ogiyo.domain.store.repository;

import com.example.ogiyo.domain.store.entity.Store;
import com.example.ogiyo.domain.store.dto.response.GetStoresResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import static com.example.ogiyo.domain.store.entity.Store.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("SELECT s FROM Store s LEFT JOIN FETCH Menu m on m.store = s WHERE s.id = :id")
    Optional<Store> findById(@Param("id") Long id);

    @Query("SELECT s FROM Store s LEFT JOIN FETCH Menu m ON m.store = s WHERE s.storeName LIKE CONCAT('%', :name, '%') OR m.menuName LIKE CONCAT('%', :name, '%')")
    List<Store> findAllByName(@Param("name") String name);

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
