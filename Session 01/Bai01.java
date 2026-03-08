import java.util.Scanner;

public class Bai01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Nhập năm sinh của bạn: ");
            String namSinhStr = sc.nextLine(); 

            int namSinh = Integer.parseInt(namSinhStr); 
            int tuoi = 2026 - namSinh;

            System.out.println("Tuổi của bạn là: " + tuoi);
        } 
        catch (NumberFormatException e) {
            System.out.println("Lỗi: Bạn phải nhập năm sinh bằng số. Ví dụ: 2000");
        } 
        finally {
            sc.close();
            System.out.println("Thực hiện dọn dẹp tài nguyên trong finally...");
        }
    }
}