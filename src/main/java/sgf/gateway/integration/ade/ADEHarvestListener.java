package sgf.gateway.integration.ade;

import org.springframework.context.ApplicationListener;
import sgf.gateway.integration.ade.opensearch.Feed;
import sgf.gateway.integration.ade.opensearch.receiver.Receiver;

public class ADEHarvestListener implements ApplicationListener<ADEHarvestEvent> {

    private final Receiver receiver;
    private final ADEFeedHarvester harvester;

    public ADEHarvestListener(Receiver receiver, ADEFeedHarvester harvester) {
        super();
        this.receiver = receiver;
        this.harvester = harvester;
    }

    @Override
    public void onApplicationEvent(ADEHarvestEvent event) {

        Runnable runnable = new ADEHarvestRunnable();
        Thread thread = new Thread(runnable);

        thread.start();
    }

    private void harvestADE() {

        Feed feed = receiver.receive();

        while (feed != null) {
            harvester.harvest(feed);
            feed = receiver.receive();
        }
    }

    private class ADEHarvestRunnable implements Runnable {

        @Override
        public void run() {
            harvestADE();
        }
    }
}
