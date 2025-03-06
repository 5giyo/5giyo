//package com.example.ogiyo.order.controller;
//
//import com.example.ogiyo.order.dto.request.AddOrderRequestDto;
//import com.example.ogiyo.order.dto.request.UpdateOrderRequestDto;
//import com.example.ogiyo.order.dto.response.OrderResponseDto;
//import com.example.ogiyo.order.service.OrderServiceImpl;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.awt.*;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/orders")
//public class OrderController {
//
//    private final OrderServiceImpl orderServiceImpl;
//
//    //주문하기 TODO: 추후에 수정예정
////    @PostMapping
////    public ResponseEntity<OrderResponseDto> addOrder(
////            @PathVariable Long userId,Long menuId,
////            @Valid @RequestBody AddOrderRequestDto addorderRequestDto
////    ) {
////        return ResponseEntity.ok(orderServiceImpl.addOrder(userId,menuId,addorderRequestDto));
////    }
//
//    //주문전체조회 TODO: 요청값찾기.
////    @GetMapping("/{userid}")
////    public ResponseEntity<List<OrderResponseDto>> findAllOrders(@PathVariable Long userId) {
////        return ResponseEntity.ok(orderServiceImpl.findAllOrders(userId));
////    }
////
//
//    //개별조회
////    @GetMapping("/{orderId}")
////    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long orderId) {
////        return ResponseEntity.ok(orderServiceImpl.findOrderById(orderId));
////    }
//
//    //주문수정
//    @PatchMapping("/{orderId}")
//    public ResponseEntity<OrderResponseDto> updateOrder(
//            @PathVariable Long orderId,
//            @Valid @RequestBody UpdateOrderRequestDto dto) {
//        orderServiceImpl.updateOrder(
//                orderId,dto.getStatus(), dto.getQuantity());
//
//        return ResponseEntity.ok().build();
//    }
//
//    //주문삭제
//    @DeleteMapping("/{orderId}")
//    public ResponseEntity<Void> deleteOrder(@PathVariable long orderId) {
//        orderServiceImpl.deleteOrder(orderId);
//        return ResponseEntity.ok().build();
//    }
//
//
//}
