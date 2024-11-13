package br.sergio.bakbata_mansion.sheet;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Stream;

@Getter
@Entity
public class Inventory {

    public static final int CAPACITY = 9;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @OneToOne(mappedBy = "inventory")
    private CharacterSheet sheet;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;
    private int capacity;

    public Inventory() {
        this(CAPACITY);
    }

    public Inventory(int capacity) {
        setCapacity(capacity);
        items = new ArrayList<>(capacity);
    }

    public void setId(UUID id) {
        this.id = Objects.requireNonNull(id, "id");
    }

    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
    }

    public List<Item> toList() {
        return new ArrayList<>(items);
    }

    public int capacity() {
        return capacity;
    }

    public Stream<Item> stream() {
        return items.stream();
    }

    public Item getFirst() {
        return items.getFirst();
    }

    public Item getLast() {
        return items.getLast();
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean contains(Item item) {
        return items.contains(item);
    }

    public Iterator<Item> iterator() {
        return items.iterator();
    }

    public Item[] toArray() {
        return items.toArray(Item[]::new);
    }

    public boolean add(Item item) {
        Objects.requireNonNull(item, "item");
        if (items.size() == capacity) {
            return false;
        }
        item.setInventory(this);
        return items.add(item);
    }

    public boolean remove(Item item) {
        item.setInventory(null);
        return items.remove(item);
    }

    public Item remove(int index) {
        Item item = items.remove(index);
        item.setInventory(null);
        return item;
    }

    public void clear() {
        items.clear();
    }

    public Item get(int index) {
        return items.get(index);
    }

    public Item set(int index, Item element) {
        if (index > capacity) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for capacity " + capacity);
        }
        Objects.requireNonNull(element, "element").setInventory(this);
        return items.set(index, element);
    }

}
