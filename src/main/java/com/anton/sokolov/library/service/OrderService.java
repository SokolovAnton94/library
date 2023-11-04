package com.anton.sokolov.library.service;

import com.anton.sokolov.library.dto.OrderDto;
import com.anton.sokolov.library.entity.Order;
import com.anton.sokolov.library.repository.OrderRepository;
import com.anton.sokolov.library.utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MappingUtils mappingUtils;

    public List<OrderDto> findAll() {
        Iterable<Order> all = orderRepository.findAll();
        return StreamSupport.stream(all.spliterator(), false)
                .map(mappingUtils::mapToOrderDto)
                .collect(Collectors.toList());
    }

    public void save(OrderDto dto) {
        Order order = mappingUtils.mapToOrder(dto);
        orderRepository.save(order);
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
