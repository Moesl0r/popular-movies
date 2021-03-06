package at.mchris.popularmovies.data;

import java.util.List;

import at.mchris.popularmovies.network.themoviedb3.Info;

/**
 * A configuration object used by the movie view model.
 */
public class Configuration {

    private boolean isIdValid = false;
    private long id;
    private final String baseUrl;
    private final List<String> posterSizes;

    public Configuration(String baseUrl, List<String> posterSizes) {
        this.baseUrl = baseUrl;
        this.posterSizes = posterSizes;
    }

    public long getId() {
        if (!isIdValid) {
            throw new IllegalAccessError("Id can't be read in invalid state");
        }
        return id;
    }

    public void setId(long id) {
        isIdValid = true;
        this.id = id;
    }

    public List<String> getPosterSizes() { return posterSizes; }
    public String getBaseUrl() { return baseUrl; }

    private String getAppropriatePosterSize(int width, int height) {
        for (String size : posterSizes) {
            if (size.equals("original")) {
                return size;
            }
            int num = Integer.valueOf(size.substring(1));
            boolean isHeight = size.charAt(0) == 'h';
            if ((isHeight && num >= height)
                    || (!isHeight && num >= width)) {
                return size;
            }
        }
        return posterSizes.get(posterSizes.size() - 1);
    }

    public String buildImageUrl(String apiKey, Movie movie, int width, int height) {
        return getBaseUrl() + getAppropriatePosterSize(width, height)
                + movie.getPosterPath() + "?" + apiKey;
    }
}
