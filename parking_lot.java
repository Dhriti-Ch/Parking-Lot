public class parking_lot {
    public static void main(String[] args){
        int numfloors = 3;
        int[][] floors = {{1,2,3},{2,5,7}};//bikes, cars, electric.
        slots[][] slot = new slots[2][14];
        for(int i = 0; i < 2; i++) {
            int j = 0;
            for (j = 0; j < floors[i][0]; j++){
                slot[i][j] = new slots(0);
            }
            for(j = j; j < floors[i][1]; j++){
                slot[i][j] = new slots(1);
            }
            for(j = j; j < floors[i][2]; j++){
                slot[i][j] = new slots(2);
            }
            for(j = j; j < 14; j++){
                slot[i][j] = new slots(-1);
            }
        }

        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 14; j++)
                System.out.print(slot[i][j].type + " ");
            System.out.println();
        }
    }
}
