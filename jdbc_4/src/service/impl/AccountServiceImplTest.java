package service.impl;

/**
 * @Description:
 * @author: lk
 * @create: 2019-12-02   22:10
 */
public class AccountServiceImplTest {
    public static void main(String[] args) {
        AccountServiceImpl service = new AccountServiceImpl();
        service.transfer(123456,654321,100);
    }
}
