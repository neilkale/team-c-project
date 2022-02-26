package edu.wpi.cs3733.c22.teamC.controllers.mapEditor;

import java.util.ArrayList;
import java.util.List;

/** @author Aidan Burns 2/8/2022 This project does MapEdit on the IntelliJ IDEA */
public class MapEdit<E> {
  private List<E> thingsRemoved;
  private List<E> thingsAdd;
  private List<E> thingsEdited;

  public MapEdit() {
    thingsRemoved = new ArrayList<>();
    thingsEdited = new ArrayList<>();
    thingsAdd = new ArrayList<>();
  }

  void remove(E e) {
    thingsRemoved.add(e);
  }

  void edit(E e) {
    thingsEdited.add(e);
  }

  void add(E e) {
    thingsAdd.add(e);
  }

  List<E> getThingsRemoved() {
    return thingsRemoved;
  }

  List<E> getThingsAdd() {
    return thingsAdd;
  }

  List<E> getThingsEdited() {
    return thingsEdited;
  }
}
