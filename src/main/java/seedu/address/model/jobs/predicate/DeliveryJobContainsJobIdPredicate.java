package seedu.address.model.jobs.predicate;

import java.util.function.Predicate;

import seedu.address.model.jobs.DeliveryJob;

/**
 * Predicate for job id.
 */
public class DeliveryJobContainsJobIdPredicate implements Predicate<DeliveryJob> {
    private final String toFind;

    public DeliveryJobContainsJobIdPredicate(String toFind) {
        this.toFind = toFind;
    }

    @Override
    public boolean test(DeliveryJob job) {
        if (job.getJobId().toUpperCase().contains(toFind.toUpperCase())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return toFind;
    }
}
