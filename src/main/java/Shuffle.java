import java.util.Random;

/**
 * Created by hucj on 2015/11/18.
 */
public class Shuffle {

    static void randomShuffle(int a[], int n) {
        Random random = new Random();
        for (int i = 0; i < n; ++i) {
            int j = random.nextInt(3) % (n - i) + i;
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
    }

    public static void main(String[] args) {
        int n = 9;
        int a[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        randomShuffle(a, n);
        for (int i = 0; i < n; ++i) {
            System.out.println(a[i]);
        }

    }
}
