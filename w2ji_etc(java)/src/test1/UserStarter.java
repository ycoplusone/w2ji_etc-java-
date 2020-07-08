package test1;
import java.util.*;

public class UserStarter {
    private static int currentDepth = 0;
    private static List<String> data = new ArrayList<>();
    private static User user;
    

    public static void main(String[] args) {
        printMenu(0);
        Scanner next = new Scanner(System.in);
        while (next.hasNext()) {
            switch (currentDepth) {
                case 0: {
                    switch (next.nextInt()) {
                        case 1: {
                            System.out.println("회원가입을 시작합니다.");
                            System.out.print("ID : ");
                            currentDepth = 2;
                            user = new User();
                        }
                        break;
                        case 2: {
                            System.out.print("ID: ");
                            currentDepth = 1;
                        }
                        break;
                        case 3: {
                            System.out.println("프로그램을 종료합니다.");
                            System.exit(0);
                        }
                        break;
                    }
                }
                break;
                case 1: {
                    data.add(next.next());
                    if (data.size() == 1) {
                        System.out.print("PW : ");
                    } else if (data.size() == 2) {
                        if (!UserManage.isLoginSuccess(data.get(0), data.get(1))) {
                            System.out.println("로그인 실패,메인 메뉴로 돌아갑니다.");
                            currentDepth = 0;
                            printMenu(0);
                        } else {
                            currentDepth = 2;
                            System.out.println("로그인 성공!");
                            printMenu(1);
                        }
                        data.clear();
                    }
                }
                break;
                case 2: {
                    data.add(next.next());
                    switch (data.size()) {
                        case 1: {
                            user.setId(data.get(data.size() - 1));
                            System.out.print("PW : ");
                        }
                        break;
                        case 2: {
                            user.setPw(data.get(data.size() - 1));
                            System.out.print("계좌번호 : ");
                        }
                        break;
                        case 3: {
                            user.setAccount_Number(data.get(data.size() - 1));
                            System.out.print("성별 : ");
                        }
                        break;
                        case 4: {
                            user.setGender(data.get(data.size() - 1));
                            System.out.print("이름 : ");
                        }
                        break;
                        case 5: {
                            user.setName(data.get(data.size() - 1));
                            System.out.print("나이: ");
                        }
                        break;
                        case 6: {
                            try {
                                user.setAge(Integer.parseInt(data.remove(data.size() - 1)));
                                System.out.println("회원가입 완료! 회원가입된 계정으로 진행합니다.");
                                UserManage.register(user);
                                AccountManage.registerAccount(user.getAccount_Number());
                                currentDepth = 3;
                                printMenu(1);
                                data.clear();
                            } catch (Exception ex) {
                                data.remove(data.size() - 1);
                                System.out.println("나이가 숫자가 아닙니다. 다시 입력하십시오.");
                            }
                        }

                    }
                }
                break;
                case 3: {
                    switch (next.nextInt()) {
                        case 1: {
                            currentDepth = 4;
                            System.out.print("한달 수입을 입력하세요 : ");
                        }
                        break;
                        case 2: {
                            currentDepth = 5;
                            System.out.print("지출 명목을 입력하세요 : ");
                        }
                        break;
                        case 3: {
                            Account ac = AccountManage.getAccount(user.getAccount_Number());
                            System.out.println("오늘의 생활비 : " + ac.getLeft() + "원");
                        }
                        break;
                        case 4: {
                            Account ac = AccountManage.getAccount(user.getAccount_Number());
                            HashMap<String, Double> total = new HashMap<>();
                            double p1 = (ac.getPerDay() / 100);
                            for (TradeType t : ac.getLogs())
                                total.put(t.getWithdrawType(), total.getOrDefault(t.getWithdrawType(), 0d) + t.getValue());
                            double total_ = 0;
                            for (Map.Entry<String, Double> entry : total.entrySet()) {
                                total_ += entry.getValue();
                                System.out.println(entry.getKey() + ": " + entry.getValue() + "원 (" + String.format("%.2f", entry.getValue() / p1) + "%)");
                            }

                            System.out.println("사용한 총 생활비 : " + total_ + "원");
                            System.out.println("사용 범위: " + String.format("%.2f", (total_ / p1)) + "%");
                        }
                        break;
                        case 5: {
                            user = null;
                            currentDepth = 0;
                            printMenu(0);
                        }
                        break;
                    }
                }
                break;
                case 4: {
                    data.add(next.next());
                    System.out.println(data);
                    if (data.size() == 1) {
                        System.out.print("저축할 금액을 입력하세요 : ");
                    } else {
                        Account ac = AccountManage.getAccount(user.getAccount_Number());
                        ac.deposit(Integer.parseInt(data.get(0)));
                        ac.withdraw("저축", Integer.parseInt(data.get(1)));
                        ac.calcPerDay();
                        currentDepth = 3;
                        int perDay = ac.getAccountBalance() / 30;
                        System.out.println("하루당 생활비 : " + perDay + "원");
                        System.out.println("저축 비용: " + data.get(1) + "원");
                        printMenu(1);
                        data.clear();
                    }

                }
                break;
                case 5: {
                    data.add(next.next());
                    if (data.size() == 1) {
                        System.out.print("지출액을 입력하세요 : ");
                    } else {
                        AccountManage.getAccount(user.getAccount_Number()).withdraw(data.get(0), Integer.parseInt(data.get(1)));
                        System.out.println("지출 명목이 추가되었습니다.");
                        currentDepth = 3;
                        printMenu(1);
                    }
                }
                break;
                case 6: {

                }
                break;
            }
        }
    }

    private static void printMenu(int type) {
        switch (type) {
            case 0:
                System.out.println("1. 회원가입");
                System.out.println("2. 로그인");
                System.out.println("3. 프로그램 종료");
                System.out.print("원하시는 메뉴를 타이핑하세요 : ");
                break;
            case 1:
                System.out.println("1. 수입 입력");
                System.out.println("2. 지출 입력");
                System.out.println("3. 오늘의 생활비");
                System.out.println("4. 소비 패턴 분석");
                System.out.println("5. 로그 아웃");
                break;
        }
    }
}
