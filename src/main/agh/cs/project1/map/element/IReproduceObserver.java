package agh.cs.project1.map.element;

@FunctionalInterface
public interface IReproduceObserver
{
    void onReproduced(Animal animal, Animal with, Animal child);
}
