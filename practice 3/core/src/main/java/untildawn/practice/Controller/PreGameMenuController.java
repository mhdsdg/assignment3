package untildawn.practice.Controller;

import untildawn.practice.Controller.GameControllers.GameController;
import untildawn.practice.Main;
import untildawn.practice.Model.App;
import untildawn.practice.Model.Enum.Heros.Hero;
import untildawn.practice.Model.Enum.Weapons.Weapons;
import untildawn.practice.Model.GameAssetManager;
import untildawn.practice.Model.Weapon;
import untildawn.practice.View.GameView;
import untildawn.practice.View.PreGameMenu;

public class PreGameMenuController {
    PreGameMenu view;

    public void setView(PreGameMenu view) {
        this.view = view;
    }

    public void handleAdvanceButton() {
        if(view != null && view.getPlayButton().isChecked()){
            switch(view.getSelectHero().getSelectedIndex()){
                case 0:
                    GameAssetManager.setHero(Hero.Shana);
                    break;
                    case 1:
                        GameAssetManager.setHero(Hero.Diamond);
                        break;
                        case 2:
                            GameAssetManager.setHero(Hero.Scarlett);
                            break;
                            case 3:
                                GameAssetManager.setHero(Hero.Lilith);
                                break;
                                case 4:
                                    GameAssetManager.setHero(Hero.Dasher);
                                    break;
            }
            switch(view.getSelectWeapon().getSelectedIndex()){
                case 0:
                    App.getWeapons().add(new Weapon());
                    GameAssetManager.setWeapon(Weapons.Shotgun);
                    App.getWeapons().add(new Weapon());
                    GameAssetManager.setWeapon(Weapons.SMG);
                    App.getWeapons().add(new Weapon());
                    break;
                    case 1:
                        GameAssetManager.setWeapon(Weapons.Shotgun);
                        App.getWeapons().add(new Weapon());
                        GameAssetManager.setWeapon(Weapons.Revolver);
                        App.getWeapons().add(new Weapon());
                        GameAssetManager.setWeapon(Weapons.SMG);
                        App.getWeapons().add(new Weapon());
                        break;
                        case 2:
                            GameAssetManager.setWeapon(Weapons.SMG);
                            App.getWeapons().add(new Weapon());
                            GameAssetManager.setWeapon(Weapons.Revolver);
                            App.getWeapons().add(new Weapon());
                            GameAssetManager.setWeapon(Weapons.Shotgun);
                            App.getWeapons().add(new Weapon());
                            break;
            }
            App.setGameDuration(Integer.parseInt(view.getSelectGameDuration().getSelected().toString()) * 60);
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new GameView(new GameController(), GameAssetManager.getManager().getSkin()));
        }
    }
}
