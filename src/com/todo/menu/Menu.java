package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println("\n<TodoList 커맨드>");
        System.out.println("1. 메모 추가 - add");
        System.out.println("2. 메모 삭제 - del");
        System.out.println("3. 메모 변경 - edit");
        System.out.println("4. 저장된 메모 - ls");
        System.out.println("5. 오름차순으로 정렬(제목 기준) - ls_name_asc");
        System.out.println("6. 내림차순으로 정렬(제목 기준) - ls_name_desc");
        System.out.println("7. 변경된 날짜를 기준으로 정렬 - ls_date");
        System.out.println("8. 프로그램 종료 - exit");
    }
    
    public static void prompt() {
    	System.out.print("\n원하는 메뉴를 입력하세요.\n커맨드 입력 > ");
	}
}
