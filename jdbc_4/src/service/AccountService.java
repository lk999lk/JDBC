package service;

/**
 * @Description:
 * @author: lk
 * @create: 2019-12-02   21:54
 */
public interface AccountService {
    void transfer(int fromCardId, int toCardID, int money);

}
