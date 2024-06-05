package dev.dex.fcpeuro.repo;

import dev.dex.fcpeuro.entity.customerorder.*;
import org.springframework.data.jpa.repository.*;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Integer> {
}
