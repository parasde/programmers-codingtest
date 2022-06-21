import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 신고결과 받기
 * https://programmers.co.kr/learn/courses/30/lessons/92334?language=java
 */

public class Coding92334 {
    // 100점
    public static int[] solution1(String[] id_list, String[] report, int k) {
        int[] answer = new int[id_list.length];

        // 신고자, 신고대상
        ArrayList<String> reporter = new ArrayList<String>();
        ArrayList<String> target = new ArrayList<String>();
        // 신고자와 신고대상을 순서대로 분리
        for (int i = 0; i < report.length; i++) {
            reporter.add(report[i].split(" ")[0]);
            target.add(report[i].split(" ")[1]);
        }

        HashMap<String, Integer> counting = new HashMap<>();
        for (int i = 0; i < id_list.length; i++) {
            String user = id_list[i];
            // user 가 신고한 타겟 카운팅
            HashMap<String, Integer> check = new HashMap<>();
            for(int n = 0; n < report.length; n++) {
                if (reporter.get(n).equals(user)) {
                    check.putIfAbsent(target.get(n), 1);
                }
            }
            check.forEach((key, value) -> {
                counting.merge(key, 1, Integer::sum);
            });
        }

        // 정지유저
        ArrayList<String> benUsers = new ArrayList<>();
        for (int i = 0; i < id_list.length; i++) {
            int count = counting.get(id_list[i]) == null ? 0 : counting.get(id_list[i]);
            if (count >= k) {
                benUsers.add(id_list[i]);
            }
        }


        // id_list 순서대로 출력
        for (int i = 0; i < id_list.length; i++) {
            String user = id_list[i];
            // user 가 신고한 타겟 카운팅
            HashMap<String, Integer> check = new HashMap<>();
            for(int n = 0; n < report.length; n++) {
                // 해당 user 의 report 정보를 불러온 후 benUsers 에 담겨있는지 확인
                if (reporter.get(n).equals(user)) {
                    // check.get(target.get(n)) 값을 확인하여 동일 신고 체크
                    if (check.get(target.get(n)) == null) {
                        check.put(target.get(n), 1);
                        for (int m = 0; m < benUsers.size(); m++) {
                            if (target.get(n).equals(benUsers.get(m))) {
                                answer[i] = answer[i] + 1;
                                break;
                            }
                        }
                    }
                }
            }
        }

        return answer;
    }

    // 80점
    public static int[] solution2(String[] id_list, String[] report, int k) {
        int[] answer = new int[id_list.length];

        // 한 유저를 여러 번 신고할 수도 있지만, 동일한 유저에 대한 신고 횟수는 1회로 처리됩니다.
        // stream distinct 를 이용하여 중복 제거
        // ex) {"ryan con", "ryan con", "ryan con", "ryan con"} -> {"ryan con"}
        List<String> reporters = Arrays.stream(report).distinct().collect(Collectors.toList());
        HashMap<String, Integer> count = new HashMap<>();
        reporters.forEach((value) -> {
            String target = value.split(" ")[1];
            // 신고받은 횟수 누적
            count.merge(target, 1, Integer::sum);
        });

        for (int i = 0; i < id_list.length; i++) {
            answer[i] = 0;
            for (String reporter: reporters) {
                String reportUser = reporter.split(" ")[0];
                // 유저별 신고현황
                if (id_list[i].equals(reportUser)) {
                    String targetUser = reporter.split(" ")[1];
                    // 누적신고가 k 이상일 경우
                    if (count.get(targetUser) >= k) {
                        answer[i] = answer[i] + 1;
                    }
                }
            }
        }

        return answer;
    }

    public static void main(String[] args) {
//        String[] id_list = {"muzi", "frodo", "apeach", "neo"};
//        String[] report = {"muzi frodo", "apeach frodo", "frodo neo", "muzi neo", "apeach muzi"};
//        int k = 2;
        String[] id_list = {"con", "ryan"};
        String[] report = {"ryan con", "ryan con", "ryan con", "ryan con"};
        int k = 1;
        int[] result = solution2(id_list, report, k);
        for (int j : result) {
            System.out.print(j);
        }
    }
}
