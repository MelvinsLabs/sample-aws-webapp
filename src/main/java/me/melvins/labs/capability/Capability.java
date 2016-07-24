/*
 *
 */

package me.melvins.labs.capability;

/**
 * @author Mels
 */
public interface Capability {

    String read(String hashKey, String rangeKey);

}
