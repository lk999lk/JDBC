package dao.impl;

import bean.Account;
import dao.AccountDao;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import util.JDBCUtil;

import java.sql.Connection;

/**
 * @Description:
 * @author: lk
 * @create: 2019-12-02   21:31
 */
public class AccountDaoImpl implements AccountDao {
    @Override
    public Account queryAccountByCard(int cardId) throws Exception {
        QueryRunner runner = new QueryRunner();

        Connection conncetion = JDBCUtil.getConncetion();
        String sql = "select * from user_table where cardId = ?";
        BeanHandler<Account> handler = new BeanHandler<>(Account.class);

        Account account = runner.query(conncetion, sql, handler, cardId);

        return account;
    }

    @Override
    public void updateAccount(Account account) throws Exception {
        QueryRunner runner = new QueryRunner();

        Connection conncetion = JDBCUtil.getConncetion();
        String sql = "update user_table set balance = ? where cardId = ?";

        runner.update(conncetion, sql, new Object[]{account.getBalance(),account.getCardId()});

    }
}
