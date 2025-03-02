package tasktracker.model;

public enum Status {
    TO_DO,
    IN_PROGRESS,
    DONE;

    public static Status fromInt(int x){
        switch (x) {
            case 1:
                return IN_PROGRESS;
            case 2:
                return DONE;
            default:
                return TO_DO;
        }
    } 
    public static int fromStatus(Status status){
        switch (status) {
            case IN_PROGRESS:
                return 1;
            case DONE:
                return 2;
            default:
                return 0;
        }
    } 
}
