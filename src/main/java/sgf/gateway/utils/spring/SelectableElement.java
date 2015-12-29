package sgf.gateway.utils.spring;

/**
 * The Class SelectableElement.
 */
public class SelectableElement<T> {

    /**
     * The selected.
     */
    private boolean selected = false;

    /**
     * The element.
     */
    private T element;

    /**
     * Instantiates a new selectable element.
     *
     * @param selected the selected
     * @param element  the element
     */
    public SelectableElement(boolean selected, T element) {

        super();
        this.selected = selected;
        this.element = element;
    }

    /**
     * Instantiates a new selectable element.
     *
     * @param element the element
     */
    public SelectableElement(T element) {

        super();
        this.element = element;
    }

    /**
     * Checks if is selected.
     *
     * @return true, if is selected
     */
    public boolean isSelected() {

        return this.selected;
    }

    /**
     * Sets the selected.
     *
     * @param selected the new selected
     */
    public void setSelected(boolean selected) {

        this.selected = selected;
    }

    /**
     * Gets the element.
     *
     * @return the element
     */
    public T getElement() {

        return this.element;
    }

    /**
     * Sets the element.
     *
     * @param element the new element
     */
    public void setElement(T element) {

        this.element = element;
    }
}
