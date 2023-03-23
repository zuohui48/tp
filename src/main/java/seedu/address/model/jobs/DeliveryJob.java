package seedu.address.model.jobs;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents delivery jobs entities.
 */
public class DeliveryJob {
    // Identity fields
    private final String jobId;

    // Delivery informations
    private final String recipient;
    private final String sender; // aka customer
    private final Optional<DeliveryDate> deliveryDate;
    private final Optional<DeliverySlot> deliverySlot;
    private final Optional<Earning> earning;
    private final Boolean isDelivered;

    /**
     * Constructs a job entity.
     *
     * @param recipient
     * @param sender
     * @param earning
     */
    public DeliveryJob(String recipient, String sender, String earning) {
        this(genJobId(recipient, sender), recipient, sender, Optional.empty(), Optional.empty(),
                Optional.of(new Earning(earning)), false);
    }

    /**
     * Constructs a job entity.
     *
     * @param recipient
     * @param sender
     * @param deliveryDate
     * @param deliverySlot
     * @param earning
     */
    public DeliveryJob(String recipient, String sender, String deliveryDate, String deliverySlot, String earning) {
        this(genJobId(recipient, sender), recipient, sender, Optional.of(new DeliveryDate(deliveryDate)),
                Optional.of(new DeliverySlot(deliverySlot)), Optional.of(new Earning(earning)), false);
    }

    /**
     * DeliveryJob
     *
     * @param jobId
     * @param recipient
     * @param deliverySlot
     * @param sender
     * @param earning
     * @param isDelivered
     */
    public DeliveryJob(String jobId, String recipient, String sender, Optional<DeliveryDate> deliveryDate,
            Optional<DeliverySlot> deliverySlot,
            Optional<Earning> earning,
            Boolean isDelivered) {
        this.jobId = jobId;
        this.recipient = recipient;
        this.sender = sender;
        this.deliveryDate = deliveryDate;
        this.deliverySlot = deliverySlot;
        this.earning = earning;
        this.isDelivered = isDelivered;
    }

    private static String genJobId(String recipient, String sender) {
        requireNonNull(recipient, sender);
        return recipient.substring(0, 2)
                .concat(sender.substring(0, 2))
                .concat(UUID.randomUUID().toString().substring(0, 6))
                .toUpperCase();
    }

    public String getJobId() {
        return jobId;
    }

    public String getRecipientId() {
        return recipient;
    }

    public String getSenderId() {
        return sender;
    }

    public Optional<DeliveryDate> getDeliveryDate() {
        return deliveryDate;
    }

    public Optional<DeliverySlot> getDeliverySlot() {
        return deliverySlot;
    }

    public Optional<Earning> getEarning() {
        return earning;
    }

    public Boolean getDeliveredStatus() {
        return isDelivered;
    }

    public LocalDate getDate() throws NoSuchElementException {
        return deliveryDate.get().getDate();
    }

    public int getSlot() throws NoSuchElementException {
        return deliverySlot.get().getSlot();
    }

    /**
     * Checks if job has delivery date and slot.
     *
     * @return boolean
     */
    public boolean isScheduled() {
        return getDeliveryDate().isPresent() && getDeliverySlot().isPresent();
    }

    /**
     * isSameDeliveryJob.
     *
     * @param otherJob
     * @return
     */
    public boolean isSameDeliveryJob(DeliveryJob otherJob) {
        if (otherJob == this) {
            return true;
        }

        return otherJob != null && otherJob.getJobId().equals(getJobId());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        String outString = "Job [%s]\n"
                + "receipent: %s\n"
                + "sender: %s\n"
                + "deliver date: %s\n"
                + "deliver slot: %s\n"
                + "earn: $%s\n"
                + "status: $%s\n";

        builder.append(
                String.format(outString,
                        jobId,
                        getRecipientId(),
                        getSenderId(),
                        getDeliveryDate().isPresent() ? getDeliveryDate().get() : "",
                        getDeliverySlot().isPresent() ? getDeliverySlot().get() : "",
                        getEarning(),
                        getDeliveredStatus()));

        return builder.toString();
    }

    /**
     * Buider class for building DeliveryJob.
     */
    public static class Builder {
        private String jobId;
        private String recipient;
        private String sender; // aka customer
        private Optional<DeliveryDate> deliveryDate = Optional.empty();
        private Optional<DeliverySlot> deliverySlot = Optional.empty();
        private Optional<Earning> earning = Optional.empty();
        private Boolean isDelivered;

        /**
         * Sets jobid.
         *
         * @param id
         * @return
         */
        public Builder jobId(String id) {
            this.jobId = id;
            return this;
        }

        /**
         * Sets recipient.
         *
         * @param id
         * @return
         */
        public Builder recipient(String id) {
            this.recipient = id;
            return this;
        }

        /**
         * Sets sender.
         *
         * @param id
         * @return
         */
        public Builder sender(String id) {
            this.sender = id;
            return this;
        }

        /**
         * Sets deliveryDate.
         *
         * @param date
         * @return
         */
        public Builder deliveryDate(String date) {
            this.deliveryDate = Optional.of(new DeliveryDate(date));
            return this;
        }

        /**
         * Sets deliverySlot.
         *
         * @param slot
         * @return
         */
        public Builder deliverySlot(String slot) {
            this.deliverySlot = Optional.of(new DeliverySlot(slot));
            return this;
        }

        /**
         * Sets earning.
         *
         * @param earn
         * @return
         */
        public Builder earning(String earn) {
            this.earning = Optional.of(new Earning(earn));
            return this;
        }

        /**
         * Sets isDelivered.
         *
         * @param isDelivered
         * @return
         */
        public Builder isDelivered(boolean isDelivered) {
            this.isDelivered = isDelivered;
            return this;
        }

        /**
         * Builds DeliveryJob.
         */
        public DeliveryJob build() {
            return new DeliveryJob(jobId, recipient, sender,
                    deliveryDate,
                    deliverySlot, earning, isDelivered);
        }
    }
}