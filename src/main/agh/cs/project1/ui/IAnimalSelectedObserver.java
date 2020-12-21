package agh.cs.project1.ui;

import agh.cs.project1.map.element.Animal;

@FunctionalInterface
public interface IAnimalSelectedObserver
{
    void onAnimalSelected(Animal animal);
}
