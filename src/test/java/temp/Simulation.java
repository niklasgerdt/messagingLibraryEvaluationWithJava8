//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.30 at 11:49:24 AM EET 
//


package temp;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="system-under-test" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="simulators">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="simulator">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                             &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="event-content-length" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                             &lt;element name="pause-between-events" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "name",
    "systemUnderTest",
    "simulators"
})
@XmlRootElement(name = "simulation")
public class Simulation {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(name = "system-under-test", required = true)
    protected String systemUnderTest;
    @XmlElement(required = true)
    protected Simulation.Simulators simulators;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the systemUnderTest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemUnderTest() {
        return systemUnderTest;
    }

    /**
     * Sets the value of the systemUnderTest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemUnderTest(String value) {
        this.systemUnderTest = value;
    }

    /**
     * Gets the value of the simulators property.
     * 
     * @return
     *     possible object is
     *     {@link Simulation.Simulators }
     *     
     */
    public Simulation.Simulators getSimulators() {
        return simulators;
    }

    /**
     * Sets the value of the simulators property.
     * 
     * @param value
     *     allowed object is
     *     {@link Simulation.Simulators }
     *     
     */
    public void setSimulators(Simulation.Simulators value) {
        this.simulators = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="simulator">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="event-content-length" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                   &lt;element name="pause-between-events" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "simulator"
    })
    public static class Simulators {

        @XmlElement(required = true)
        protected Simulation.Simulators.Simulator simulator;

        /**
         * Gets the value of the simulator property.
         * 
         * @return
         *     possible object is
         *     {@link Simulation.Simulators.Simulator }
         *     
         */
        public Simulation.Simulators.Simulator getSimulator() {
            return simulator;
        }

        /**
         * Sets the value of the simulator property.
         * 
         * @param value
         *     allowed object is
         *     {@link Simulation.Simulators.Simulator }
         *     
         */
        public void setSimulator(Simulation.Simulators.Simulator value) {
            this.simulator = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="event-content-length" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *         &lt;element name="pause-between-events" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "id",
            "name",
            "eventContentLength",
            "pauseBetweenEvents"
        })
        public static class Simulator {

            @XmlElement(required = true)
            protected BigInteger id;
            @XmlElement(required = true)
            protected String name;
            @XmlElement(name = "event-content-length", required = true, defaultValue = "100")
            protected BigInteger eventContentLength;
            @XmlElement(name = "pause-between-events", required = true, defaultValue = "10000")
            protected BigInteger pauseBetweenEvents;

            /**
             * Gets the value of the id property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getId() {
                return id;
            }

            /**
             * Sets the value of the id property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setId(BigInteger value) {
                this.id = value;
            }

            /**
             * Gets the value of the name property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getName() {
                return name;
            }

            /**
             * Sets the value of the name property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setName(String value) {
                this.name = value;
            }

            /**
             * Gets the value of the eventContentLength property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getEventContentLength() {
                return eventContentLength;
            }

            /**
             * Sets the value of the eventContentLength property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setEventContentLength(BigInteger value) {
                this.eventContentLength = value;
            }

            /**
             * Gets the value of the pauseBetweenEvents property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getPauseBetweenEvents() {
                return pauseBetweenEvents;
            }

            /**
             * Sets the value of the pauseBetweenEvents property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setPauseBetweenEvents(BigInteger value) {
                this.pauseBetweenEvents = value;
            }

        }

    }

}
