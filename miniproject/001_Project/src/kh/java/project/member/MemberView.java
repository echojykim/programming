package kh.java.project.member;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import kh.java.project.db.DbMemberManager;
import kh.java.project.main.CinemaView;

public class MemberView {
	private DbMemberManager dbm = new DbMemberManager(); 
	private Scanner sc = new Scanner(System.in);
	boolean flag = false;
	List<Member> memberAddList = new ArrayList<Member>();
	List<Member> check = new ArrayList<>();
	CinemaView cinemaview = new CinemaView(); 
	
	static String fileName = "dbList/MemberList.txt";
	static File memberDbFile = new File(fileName);
	
	
	Member member = new Member();
	String inputId = null;
	String inputPw = null;
	
	public void mainMenu() {
		int select = 0;
		
		String menu = ">>>>>>>> ๐ขLogin Menu <<<<<<<\n"
				+ "1. ๋ก๊ทธ์ธ ๐\n"
				+ "2. ํ์๊ฐ์ ๐พ\n"
				+ "3. ๊ณ์  ์ญ์  โ\n"
				+ "4. ์์ด๋ ์ฐพ๊ธฐ ๐\n"
				+ "5. ๋น๋ฐ๋ฒํธ ์ฐพ๊ธฐ ๐\n"
				+ "0. ๋๊ฐ๊ธฐ ๐ด\n"
				+ "-----------------------------\n"
				+ "> ๋ฉ๋ด ์ ํ: ";
		while(true) {
			System.out.print(menu);
			try {
				
				select = sc.nextInt();
				switch(select) {
				case 1 : 
					login();
					cinemaview.mainMenu();
					break;
				case 2 : mbAdd(); break;
				case 3 : mbRemove(); break;
				case 4 : findId(); break;
				case 5 : findPw(); break;
				case 0 : 
					logout();
					if(flag == true)
						return;
					break;
				default:
					System.out.println("์๋ชป ์๋ ฅํ์จ์ต๋๋ค. ๋ค์ ์ ํํด์ฃผ์ธ์~");
				}
			} catch(InputMismatchException e) {
				System.err.println("์ซ์๋ง ์๋ ฅํด์ฃผ์ธ์~");
				sc.nextLine();
			}
		}
	}
	
	public void login() {
		outer:
		while(true) {
			int pass = 0;
			System.out.print("์์ด๋๋ฅผ ์๋ ฅํ์ธ์: ");
			String inputId = sc.next();
			System.out.print("๋น๋ฐ๋ฒํธ๋ฅผ ์๋ ฅํ์ธ์: ");
			String inputPw = sc.next();
			try {
				BufferedReader br = new BufferedReader(new FileReader(memberDbFile));
				String line = "";
				
				try {
					while((line = br.readLine()) != null) {
						int passId = line.indexOf(inputId);
						int passPw = line.indexOf(inputPw);
						if(passId != -1 && passPw != -1) {
							System.out.println(inputId + "๋ ํ์ํฉ๋๋ค.");
							pass = 1;
							CinemaView loingCinema = new CinemaView();
							loingCinema.loginUserId(inputId);
							return;
						}
					}
					if(pass == 0) {
						System.out.println("๋ค์ ์๋ ฅํด์ฃผ์ธ์~");
					} 
					br.close();
				} catch (IOException | NullPointerException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * ํ์๊ฐ์
	 */
	public void mbAdd() {
		System.out.print("์์ด๋๋ฅผ ์๋ ฅํด์ฃผ์ธ์(๊ณต๋ฐฑ ๋ถ๊ฐ): ");
		String userId = sc.next().replace("[ ~!@#$%^&*]", "");
		System.out.print("๋น๋ฐ๋ฒํธ๋ฅผ ์๋ ฅํด์ฃผ์ธ์: ");
		String userPw = sc.next();
		System.out.print("์ด๋ฆ์ ์๋ ฅํด์ฃผ์ธ์: ");
		sc.nextLine();
		String userName = sc.nextLine().replace(" ", "");
		System.out.print("๋์ด๋ฅผ ์๋ ฅํด์ฃผ์ธ์: ");
		int age = sc.nextInt();
		System.out.print("ํธ๋ํฐ ๋ฒํธ๋ฅผ ์๋ ฅํด์ฃผ์ธ์: ");
		sc.nextLine();
		String userPhone = sc.nextLine().replace("[- ]", "");
		Member mb = new Member(userId, userPw, userName, age, userPhone);
		DbMemberManager.memberAddList(mb);
		System.out.println("ํ์๊ฐ์์ด ์๋ฃ๋์์ต๋๋ค.");
		return;
	}
	
	/**
	 * ์ ๋ณด ์ญ์  ๋ฉ์๋
	 */
	public void mbRemove() {
		System.out.print("> ์ญ์ ํ  ๊ณ์  ์์ด๋๋ฅผ ์๋ ฅํด์ฃผ์ธ์: ");
		String removeId = sc.next();
		System.out.print("> ๋น๋ฐ๋ฒํธ๋ฅผ ์๋ ฅํด์ฃผ์ธ์: ");
		String removePw = sc.next();
		DbMemberManager.memberRemoveList(removeId, removePw);
	}
	/**
	 * 
	 */
	public void findId() {
		System.out.println("-----------------------------");
		System.out.print("์ฐพ์ผ์ค ์์ด๋์ ๋ฑ๋ก๋ ํธ๋ํฐ ๋ฒํธ๋ฅผ ์๋ ฅํ์ธ์: ");
		sc.nextLine();
		String findId = sc.nextLine();
		DbMemberManager.memberFindIdList(findId);
	}
	/**
	 * 
	 */
	public void findPw() {
		System.out.println("-----------------------------");
		System.out.print("์์ด๋๋ฅผ ์๋ ฅํ์ธ์: ");
		sc.nextLine();
		String findPw = sc.nextLine();
		DbMemberManager.memberFindPwList(findPw);
	}
	
	/**
	 * ์ข๋ฃ๋ฉ์๋
	 */
	public void logout() {
		while(true) {
			System.out.print("์ข๋ฃํ์๊ฒ ์ต๋๊น?(y/n): ");
			char yn = sc.next().charAt(0);
			
			switch(yn) {
			case 'y':
			case 'Y':
				System.out.println("ํ๋ก๊ทธ๋จ์ ์ข๋ฃํฉ๋๋ค.");
				flag = true;
				return;
			case 'n':
			case 'N':
				return;
			default:
				System.out.println("์๋ชป ์๋ ฅํ์จ์ต๋๋ค. ๋ค์ ์๋ ฅํด์ฃผ์ธ์~");
			}
		}
	}
}
