package com.cody.codystage.common.transaction;

import com.cody.codystage.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

/**
 * redis和数据库并行事务
 */
@Component
@Scope(ConfigurableListableBeanFactory.SCOPE_PROTOTYPE)
public class RedisDataSourceTransaction {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    public TransactionStatus begin() {
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(new DefaultTransactionAttribute());
        redisUtil.begin();
        return transactionStatus;
    }

    public void commit(TransactionStatus transactionStatus) throws Exception {
        if (transactionStatus == null) {
            throw new Exception("transactionStatus is null");
        }

        dataSourceTransactionManager.commit(transactionStatus);
        redisUtil.exec();
    }

    public void rollback(TransactionStatus transactionStatus) throws Exception {
        if (transactionStatus == null) {
            throw new Exception("transactionStatus is null");
        }
        dataSourceTransactionManager.rollback(transactionStatus);
        redisUtil.discard();
    }


}
