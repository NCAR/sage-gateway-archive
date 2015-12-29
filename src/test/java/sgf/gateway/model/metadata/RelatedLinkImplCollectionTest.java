package sgf.gateway.model.metadata;

import org.junit.Test;
import org.safehaus.uuid.UUID;
import sgf.gateway.utils.hibernate.CollectionMerger;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RelatedLinkImplCollectionTest {

    @Test
    public void twoEmptyCollections() {

        List originalList = new ArrayList<RelatedLink>();
        List newList = new ArrayList<RelatedLink>();

        CollectionMerger.mergeList(originalList, newList);

        assertThat(originalList.isEmpty(), is(true));
    }

    @Test(expected = NullPointerException.class)
    public void nullNewCollectionCausesException() {

        List originalList = new ArrayList<RelatedLink>();
        List newList = null;

        CollectionMerger.mergeList(originalList, newList);
    }

    @Test
    public void emptyOriginalListIfNewListIsEmpty() {

        List originalList = new ArrayList<RelatedLink>();
        originalList.add(new RelatedLinkImpl(UUID.valueOf("1c6b77fd-345e-49db-9d24-b43ca8a26539"), "Link Text", URI.create("http://link.com")));

        List newList = new ArrayList<RelatedLink>();

        CollectionMerger.mergeList(originalList, newList);

        assertThat(originalList.isEmpty(), is(true));
    }

    @Test
    public void newListWithNewItemAddsNewItem() {

        List originalList = new ArrayList<RelatedLink>();

        List newList = new ArrayList<RelatedLink>();
        RelatedLink relatedLink = new RelatedLinkImpl(UUID.valueOf("1c6b77fd-345e-49db-9d24-b43ca8a26539"), "Link Text", URI.create("http://link.com"));
        newList.add(relatedLink);

        CollectionMerger.mergeList(originalList, newList);

        assertThat(originalList.contains(relatedLink), is(true));
    }

    @Test
    public void newListWithUnchangedContentRetainsOriginalItem() {

        List<RelatedLink> originalList = new ArrayList<RelatedLink>();
        RelatedLink originalRelatedLink = new RelatedLinkImpl(UUID.valueOf("b6f1a59c-0ee5-470e-b918-3e1e5b34b71f"), "Link Text", URI.create("http://link.com"));
        originalList.add(originalRelatedLink);

        List<RelatedLink> newList = new ArrayList<RelatedLink>();
        RelatedLink newRelatedLink = new RelatedLinkImpl(UUID.valueOf("1c6b77fd-345e-49db-9d24-b43ca8a26539"), "Link Text", URI.create("http://link.com"));
        newList.add(newRelatedLink);

        CollectionMerger.mergeList(originalList, newList);

        assertThat(originalList.size(), is(1));
        assertThat(originalList.contains(originalRelatedLink), is(true));
        assertThat(originalList.get(0).getIdentifier(), is(UUID.valueOf("b6f1a59c-0ee5-470e-b918-3e1e5b34b71f")));
    }

    @Test
    public void newListWithUnchangedContentRetainsOriginalItems() {

        List<RelatedLink> originalList = new ArrayList<RelatedLink>();
        originalList.add(new RelatedLinkImpl(UUID.valueOf("b6f1a59c-0ee5-470e-b918-3e1e5b34b71f"), "Link Text", URI.create("http://link.com")));
        originalList.add(new RelatedLinkImpl(UUID.valueOf("34a4bf5e-da3b-4903-9b25-9a52662de8d8"), "Link Text 2", URI.create("http://link2.com")));

        List<RelatedLink> newList = new ArrayList<RelatedLink>();
        newList.add(new RelatedLinkImpl(UUID.valueOf("be1433df-cd4c-466d-8f93-0370cd4434ad"), "Link Text", URI.create("http://link.com")));
        newList.add(new RelatedLinkImpl(UUID.valueOf("922e26f2-b812-418e-b55a-f777558a6b55"), "Link Text 2", URI.create("http://link2.com")));

        CollectionMerger.mergeList(originalList, newList);

        assertThat(originalList.size(), is(2));

        assertThat(originalList.get(0).getIdentifier(), is(UUID.valueOf("b6f1a59c-0ee5-470e-b918-3e1e5b34b71f")));
        assertThat(originalList.get(1).getIdentifier(), is(UUID.valueOf("34a4bf5e-da3b-4903-9b25-9a52662de8d8")));
    }

    @Test
    public void newListWithChangedSecondItemReplacesOriginalSecondItem() {

        List<RelatedLink> originalList = new ArrayList<RelatedLink>();
        originalList.add(new RelatedLinkImpl(UUID.valueOf("b6f1a59c-0ee5-470e-b918-3e1e5b34b71f"), "Link Text", URI.create("http://link.com")));
        originalList.add(new RelatedLinkImpl(UUID.valueOf("34a4bf5e-da3b-4903-9b25-9a52662de8d8"), "Link Text 2", URI.create("http://link2.com")));

        List<RelatedLink> newList = new ArrayList<RelatedLink>();
        newList.add(new RelatedLinkImpl(UUID.valueOf("be1433df-cd4c-466d-8f93-0370cd4434ad"), "Link Text", URI.create("http://link.com")));
        newList.add(new RelatedLinkImpl(UUID.valueOf("922e26f2-b812-418e-b55a-f777558a6b55"), "Link Text 3", URI.create("http://link3.com")));

        CollectionMerger.mergeList(originalList, newList);

        assertThat(originalList.size(), is(2));

        assertThat(originalList.get(0).getIdentifier(), is(UUID.valueOf("b6f1a59c-0ee5-470e-b918-3e1e5b34b71f")));
        assertThat(originalList.get(1).getIdentifier(), is(UUID.valueOf("922e26f2-b812-418e-b55a-f777558a6b55")));
    }

    @Test
    public void originalListReferenceUnchanged() {

        List<RelatedLink> originalList = new ArrayList<RelatedLink>();
        List<RelatedLink> originalListRef = originalList;

        originalList.add(new RelatedLinkImpl(UUID.valueOf("b6f1a59c-0ee5-470e-b918-3e1e5b34b71f"), "Link Text", URI.create("http://link.com")));
        originalList.add(new RelatedLinkImpl(UUID.valueOf("34a4bf5e-da3b-4903-9b25-9a52662de8d8"), "Link Text 2", URI.create("http://link2.com")));

        assertThat(originalList == originalListRef, is(true));

        List<RelatedLink> newList = new ArrayList<RelatedLink>();
        newList.add(new RelatedLinkImpl(UUID.valueOf("be1433df-cd4c-466d-8f93-0370cd4434ad"), "Link Text", URI.create("http://link.com")));
        newList.add(new RelatedLinkImpl(UUID.valueOf("922e26f2-b812-418e-b55a-f777558a6b55"), "Link Text 3", URI.create("http://link3.com")));

        CollectionMerger.mergeList(originalList, newList);
        assertThat(originalList == originalListRef, is(true));
    }

    @Test
    public void newListWithChangedFirstItemReplacesOriginalFirstItem() {

        List<RelatedLink> originalList = new ArrayList<RelatedLink>();
        originalList.add(new RelatedLinkImpl(UUID.valueOf("b6f1a59c-0ee5-470e-b918-3e1e5b34b71f"), "Link Text", URI.create("http://link.com")));
        originalList.add(new RelatedLinkImpl(UUID.valueOf("34a4bf5e-da3b-4903-9b25-9a52662de8d8"), "Link Text 2", URI.create("http://link2.com")));

        List<RelatedLink> newList = new ArrayList<RelatedLink>();
        newList.add(new RelatedLinkImpl(UUID.valueOf("be1433df-cd4c-466d-8f93-0370cd4434ad"), "Link Text 3", URI.create("http://link3.com")));
        newList.add(new RelatedLinkImpl(UUID.valueOf("922e26f2-b812-418e-b55a-f777558a6b55"), "Link Text 2", URI.create("http://link2.com")));

        CollectionMerger.mergeList(originalList, newList);

        assertThat(originalList.size(), is(2));

        assertThat(originalList.get(0).getIdentifier(), is(UUID.valueOf("be1433df-cd4c-466d-8f93-0370cd4434ad")));
        assertThat(originalList.get(1).getIdentifier(), is(UUID.valueOf("34a4bf5e-da3b-4903-9b25-9a52662de8d8")));
    }

    @Test
    public void newListWithSwitchedItemsCausesOriginalListItemSwitch() {

        List<RelatedLink> originalList = new ArrayList<RelatedLink>();
        originalList.add(new RelatedLinkImpl(UUID.valueOf("b6f1a59c-0ee5-470e-b918-3e1e5b34b71f"), "Link Text", URI.create("http://link.com")));
        originalList.add(new RelatedLinkImpl(UUID.valueOf("34a4bf5e-da3b-4903-9b25-9a52662de8d8"), "Link Text 2", URI.create("http://link2.com")));

        List<RelatedLink> newList = new ArrayList<RelatedLink>();
        newList.add(new RelatedLinkImpl(UUID.valueOf("be1433df-cd4c-466d-8f93-0370cd4434ad"), "Link Text 2", URI.create("http://link2.com")));
        newList.add(new RelatedLinkImpl(UUID.valueOf("922e26f2-b812-418e-b55a-f777558a6b55"), "Link Text", URI.create("http://link.com")));

        CollectionMerger.mergeList(originalList, newList);

        assertThat(originalList.size(), is(2));

        assertThat(originalList.get(0).getIdentifier(), is(UUID.valueOf("34a4bf5e-da3b-4903-9b25-9a52662de8d8")));
        assertThat(originalList.get(1).getIdentifier(), is(UUID.valueOf("b6f1a59c-0ee5-470e-b918-3e1e5b34b71f")));
    }


    @Test
    public void newListWithDistantSwitchedItemsCausesOriginalListItemSwitch() {

        List<RelatedLink> originalList = new ArrayList<RelatedLink>();
        originalList.add(new RelatedLinkImpl(UUID.valueOf("b6f1a59c-0ee5-470e-b918-3e1e5b34b71f"), "Link Text", URI.create("http://link.com")));
        originalList.add(new RelatedLinkImpl(UUID.valueOf("2c5e2e70-cfef-11e3-9c1a-0800200c9a66"), "Link Text Mid", URI.create("http://linkMid.com")));
        originalList.add(new RelatedLinkImpl(UUID.valueOf("34a4bf5e-da3b-4903-9b25-9a52662de8d8"), "Link Text 2", URI.create("http://link2.com")));

        List<RelatedLink> newList = new ArrayList<RelatedLink>();
        newList.add(new RelatedLinkImpl(UUID.valueOf("be1433df-cd4c-466d-8f93-0370cd4434ad"), "Link Text 2", URI.create("http://link2.com")));
        newList.add(new RelatedLinkImpl(UUID.valueOf("3c5e2e70-cfef-11e3-9c1a-0800200c9a66"), "Link Text Mid", URI.create("http://linkMid.com")));
        newList.add(new RelatedLinkImpl(UUID.valueOf("922e26f2-b812-418e-b55a-f777558a6b55"), "Link Text", URI.create("http://link.com")));

        CollectionMerger.mergeList(originalList, newList);

        assertThat(originalList.size(), is(3));

        assertThat(originalList.get(0).getIdentifier(), is(UUID.valueOf("34a4bf5e-da3b-4903-9b25-9a52662de8d8")));
        assertThat(originalList.get(1).getIdentifier(), is(UUID.valueOf("2c5e2e70-cfef-11e3-9c1a-0800200c9a66")));
        assertThat(originalList.get(2).getIdentifier(), is(UUID.valueOf("b6f1a59c-0ee5-470e-b918-3e1e5b34b71f")));
    }


}
