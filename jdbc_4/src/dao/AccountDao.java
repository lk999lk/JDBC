package dao;

import bean.Account;

/**
 * @Description:
 * @author: lk
 * @create: 2019-12-02   21:24
 */
public interface AccountDao {
    //根据卡号 查询账户
    Account queryAccountByCard(int cardId) throws Exception;

    //修改账户
    void updateAccount(Account account) throws Exception;

}
