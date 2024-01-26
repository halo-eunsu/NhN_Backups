public class Account {

    //계좌번호는 편의를 위해서 1,2,3,4.... 형식으로 사용합니다.
    private long accountNumber;
    //이름
    private String name;
    //잔고
    private long balance;

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }

}