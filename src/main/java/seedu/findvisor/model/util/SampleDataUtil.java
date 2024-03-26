package seedu.findvisor.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.findvisor.commons.util.DateTimeUtil;
import seedu.findvisor.model.AddressBook;
import seedu.findvisor.model.ReadOnlyAddressBook;
import seedu.findvisor.model.person.Address;
import seedu.findvisor.model.person.Email;
import seedu.findvisor.model.person.Meeting;
import seedu.findvisor.model.person.Name;
import seedu.findvisor.model.person.Person;
import seedu.findvisor.model.person.Phone;
import seedu.findvisor.model.person.Remark;
import seedu.findvisor.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@gmail.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), getTagSet("LimFamily", "Father", "PRUGrowth",
                    "PRUtravelsafe"),
                convertToMeeting(new String[] {"23-05-2024T16:00", "23-05-2024T18:00", "Online Meeting"}),
                Optional.of(new Remark("Wants to move to the new house by next January"))),
            new Person(new Name("Elizabeth Yeoh"), new Phone("89334567"), new Email("elyyeoh@gmail.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("Mother", "LimFamily", "PRUGrowth"),
        Optional.empty(), Optional.of(new Remark("Also wants to move to the new house by next January"))),
            new Person(new Name("Don Yeoh"), new Phone("99126297"), new Email("donyeoh@gmail.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("LimFamily", "Child", "PRUGrowth"),
        Optional.empty(), Optional.of(new Remark("Still schooling"))),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@hotmail.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("DavidGirlfriend", "PRUgain365"),
                convertToMeeting(new String[] {"16-04-2024T13:00", "16-04-2024T15:00",
                "Physical meeting at Serangoon Gardens"}),
                Optional.of(new Remark("Working as SWE, wants to BTO with David"))),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@hotmail.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("BerniceBoyfriend", "PRUgain365"),
                convertToMeeting(new String[] {"16-04-2024T13:00", "16-04-2024T15:00",
                "Physical meeting at Serangoon Gardens"}),
                Optional.of(new Remark("Still schooling, wants to BTO with Bernice"))),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a meeting object based on strings given.
     */
    public static Optional<Meeting> convertToMeeting(String[] meetingParameters) {
        LocalDateTime startDateTime = DateTimeUtil.parseDateTimeString(meetingParameters[0]);
        LocalDateTime endDateTime = DateTimeUtil.parseDateTimeString(meetingParameters[1]);
        String meetingRemark = meetingParameters[2];

        return Optional.of(new Meeting(startDateTime, endDateTime, meetingRemark));
    }


}
