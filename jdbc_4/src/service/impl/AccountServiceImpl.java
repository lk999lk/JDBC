package service.impl;

import bean.Account;
import dao.impl.AccountDaoImpl;
import service.AccountService;
import util.JDBCUtil;

/**
 * @Description:
 * @author: lk
 * @create: 2019-12-02   21:56
 */
public class AccountServiceImpl implements AccountService {
    @Override
    public void transfer(int fromCardId, int toCardID, int money) {
        AccountDaoImpl accountDao = new AccountDaoImpl();
        //开启事务
        try {
            JDBCUtil.beginTransaction();
            //各种DML操作
            //根据cardId查询响应的账户
            Account fromAccount = accountDao.queryAccountByCard(fromCardId);
            Account toAccount = accountDao.queryAccountByCard(toCardID);

            //转账操作
            if (fromAccount.getBalance() >= money){
                int fromBalance = fromAccount.getBalance() - money;
                fromAccount.setBalance(fromBalance);
//                fromAccount.setBalance(fromCardId);
                System.out.println(fromAccount.getCardId()+"---"+fromAccount.getBalance());

                accountDao.updateAccount(fromAccount);

                int toBalance = toAccount.getBalance() + money;
                toAccount.setBalance(toBalance);
//                toAccount.setCardId(toCardID);
                System.out.println(toAccount.getCardId()+"---"+toAccount.getBalance());

                accountDao.updateAccount(toAccount);


                System.out.println("转账成功！");

                //正常提交
                JDBCUtil.commitTransaction();
            } else{
                System.out.println("余额不足!");
            }

        } catch (Exception e) {
            try {
                JDBCUtil.rollbackTransaction();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            //结束事务
            JDBCUtil.close();
        }

    }
}
