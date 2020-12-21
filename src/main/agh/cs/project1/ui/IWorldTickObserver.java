package agh.cs.project1.ui;

import agh.cs.project1.IEngine;

@FunctionalInterface
public interface IWorldTickObserver
{
    void onWorldTick(IEngine engine);
}
