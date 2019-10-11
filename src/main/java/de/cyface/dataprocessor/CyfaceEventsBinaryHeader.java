package de.cyface.dataprocessor;

public class CyfaceEventsBinaryHeader {
    private short formatVersion;
    private int numberOfEvents;
    private int beginOfEventsIndex;

    public short getFormatVersion() {
        return formatVersion;
    }

    public void setFormatVersion(short formatVersion) {
        this.formatVersion = formatVersion;
    }

    public int getNumberOfEvents() {
        return numberOfEvents;
    }

    public void setNumberOfEvents(int numberOfEvents) {
        this.numberOfEvents = numberOfEvents;
    }

    public int getBeginOfEventsIndex() {
        return beginOfEventsIndex;
    }

    public void setBeginOfEventsIndex(int beginOfEventsIndex) {
        this.beginOfEventsIndex = beginOfEventsIndex;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Cyface Events Format Version: ").append(getFormatVersion()).append("\n")
                .append("# events: ").append(getNumberOfEvents()).append("\n");
        return sb.toString();
    }
}
