package io.mvpstarter.sample.features.main;

import javax.inject.Inject;

import io.mvpstarter.sample.data.DataManager;
import io.mvpstarter.sample.features.base.BasePresenter;
import io.mvpstarter.sample.injection.ConfigPersistent;
import io.mvpstarter.sample.util.rx.scheduler.SchedulerUtils;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager dataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    public void getPokemon(int limit) {
        checkViewAttached();
        getMvpView().showProgress(true);
        dataManager
                .getPokemonList(limit)
                .compose(SchedulerUtils.ioToMain())
                .subscribe(
                        pokemons -> {
                            getMvpView().showProgress(false);
                            getMvpView().showPokemon(pokemons);
                        },
                        throwable -> {
                            getMvpView().showProgress(false);
                            getMvpView().showError(throwable);
                        });
    }
}