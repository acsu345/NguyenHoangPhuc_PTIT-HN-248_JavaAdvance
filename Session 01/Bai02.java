import java.util.Scanner;

public class Bai02{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Nhập tổng số người dùng: ");
            int tongSoNguoi = sc.nextInt();

            System.out.print("Nhập số nhóm muốn chia: ");
            int soNhom = sc.nextInt();
            int soNguoiMoiNhom = tongSoNguoi / soNhom;

            System.out.println("Mỗi nhóm có: " + soNguoiMoiNhom + " người");
        } 
        catch (ArithmeticException e) {
            System.out.println("Không thể chia cho 0!");
        }

        System.out.println("Chương trình vẫn tiếp tục chạy...");
        sc.close();
    }
}