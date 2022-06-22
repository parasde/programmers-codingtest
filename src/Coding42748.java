import java.util.Arrays;

/**
 * K번째 수
 * https://programmers.co.kr/learn/courses/30/lessons/42748
 */

public class Coding42748 {
    // accuracy 100
    public static int[] solution(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];

        for (int x = 0; x < commands.length; x++) {
            int i = commands[x][0] - 1;
            int j = commands[x][1] - 1;
            int k = commands[x][2] - 1;

            int[] nArray = new int[j - i + 1];
            int index = 0;
            for (int y = i; y <= j; y++) {
                nArray[index] = array[y];
                index++;
            }
            Arrays.sort(nArray);
            answer[x] = nArray[k];
        }

        return answer;
    }

    public static void main (String[] args) {
        int[] array = {1, 5, 2, 6, 3, 7, 4};
        int[][] commands = {{2, 5, 3}, {4, 4, 1}, {1, 7, 3}};
        int[] result = solution(array, commands);
        for (int j: result) {
            System.out.println(j);
        }
    }
}
