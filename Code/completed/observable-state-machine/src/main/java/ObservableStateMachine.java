public class ObservableStateMachine {
    private final StateNotifier notifier;
    private State state;

    public ObservableStateMachine() {
        this(new NoOpStateNotifier());
    }

    public ObservableStateMachine(StateNotifier notifier) {
        this.notifier = notifier;
        this.state = State.Initial;
    }

    public void insertCoin() {
        switch (state) {
            case Initial -> {
                state = State.CoinInserted;
                notifier.notify(state);
            }
            case DrinkSelected -> {
                state = State.DrinkDelivered;
                notifier.notify(state);
            }
            case CoinInserted, DrinkDelivered -> {
            }
        }
    }

    public void selectDrink() {
        switch (state) {
            case Initial -> {
                state = State.DrinkSelected;
                notifier.notify(state);
            }
            case CoinInserted -> {
                state = State.DrinkDelivered;
                notifier.notify(state);
            }
            case DrinkSelected, DrinkDelivered -> {
            }
        }
    }

    public void takeDrink() {
        switch (state) {
            case DrinkDelivered -> {
                System.out.println("Enjoy your drink!");
                state = State.Initial;
                notifier.notify(state);
            }
            case Initial, CoinInserted, DrinkSelected -> {
            }
        }
    }
}