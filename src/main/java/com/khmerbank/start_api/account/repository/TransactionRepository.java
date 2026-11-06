package com.khmerbank.start_api.account.repository;
import com.khmerbank.start_api.account.model.Transaction;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long>{
    // ស្វែងរកប្រវត្តិប្រតិបត្តិការទាំងអស់ដែលពាក់ព័ន្ធនឹងគណនីមួយ (ទាំងផ្ទេរចេញ និងទទួលចូល)
    List<Transaction> findBySourceAccountIdOrTargetAccountIdOrderByCreatedAtDesc(Long sourceAccountId, Long targetAccountId);

}
