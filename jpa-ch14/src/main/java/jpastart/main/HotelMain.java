package jpastart.main;

import jpastart.jpa.EMF;
import jpastart.reserve.application.GetHotelSummaryService;
import jpastart.reserve.application.HotelNotFoundException;
import jpastart.reserve.application.HotelSummary;
import jpastart.reserve.model.Hotel;
import jpastart.reserve.model.Review;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class HotelMain {
    private static GetHotelSummaryService hotelSummaryService =
            new GetHotelSummaryService();

    public static void main(String[] args) throws IOException {
        EMF.init();

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                System.out.println("명령어를 입력하세요:");
                String line = reader.readLine();
                String[] commands = line.split(" ");
                if (commands[0].equalsIgnoreCase("exit")) {
                    System.out.println("종료합니다");
                    break;
                } else if (commands[0].equalsIgnoreCase("view")) {
                    handleViewCommand(commands);
                } else {
                    System.out.println("올바른 명령어를 입력하세요.");
                }
                System.out.println("----");
            }
        } finally {
            EMF.close();
        }
    }

    private static void handleViewCommand(String[] commands) {
        if (commands.length == 1) {
            printHelp();
        } else {
            String hotelId = commands[1];
            try {
                HotelSummary hotelSummary =
                        hotelSummaryService.getHotelSummary(hotelId);
                Hotel hotel = hotelSummary.getHotel();
                System.out.printf("ID: %s\n이름: %s\n등급: %s\n",
                        hotel.getId(), hotel.getName(), hotel.getGrade().name());
                List<Review> reviews = hotelSummary.getLatestReviews();
                if (reviews.isEmpty()) {
                    System.out.println("* 리뷰 없음");
                } else {
                    reviews.forEach(review ->
                            System.out.printf(
                                    "리뷰 점수: %d, 내용: %s\n",
                                    review.getRate(), review.getComment())
                    );
                }
            } catch (HotelNotFoundException e) {
                System.out.printf("호텔[%s] 정보가 없습니다.\n", hotelId);
            }
        }
    }

    private static void printHelp() {
        System.out.println("사용법: view 호텔ID");
    }
}
