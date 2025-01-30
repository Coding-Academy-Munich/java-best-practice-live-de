public class StateMachine {
    private State state;

    public StateMachine() {
        this.state = State.Initial;
    }

    public void insertCoin() {
        switch (state) {
            case Initial -> state = State.CoinInserted;
            case DrinkSelected -> state = State.DrinkDelivered;
            case CoinInserted, DrinkDelivered -> {
            }
        }
    }

    public void selectDrink() {
        switch (state) {
            case Initial -> state = State.DrinkSelected;
            case CoinInserted -> state = State.DrinkDelivered;
            case DrinkSelected, DrinkDelivered -> {
            }
        }
    }

    public void takeDrink() {
        switch (state) {
            case DrinkDelivered -> {
                System.out.println("Enjoy your drink!");
                state = State.Initial;
            }
            case Initial, CoinInserted, DrinkSelected -> {
            }
        }
    }
}