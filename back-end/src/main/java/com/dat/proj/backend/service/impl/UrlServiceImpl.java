package com.dat.proj.backend.service.impl;

import com.dat.proj.backend.entity.Url;
import com.dat.proj.backend.repository.UrlRepository;
import com.dat.proj.backend.util.CinemaGruvhException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

public class UrlServiceImpl {
    private String allowedCharString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private char[] allowedChars = allowedCharString.toCharArray();

    @Autowired
    private UrlRepository urlRepository;

    /**
     * Idea is to store the longUrl to DB and get the id from the db for this entry
     * Now that id will be integer, So convert that id to base 64 based on allowed
     * chars return the converted string
     *
     * @param url
     * @return
     */
    public String convertToShortUrl(Url url) {
        // first save the long url to db
        url.setCreatedOn(LocalDateTime.now());
        Url savedUrl = this.urlRepository.save(url);
        long storedLongUrlid = savedUrl.getId();
        return convertToShortUrl(storedLongUrlid);
    }

    /**
     * get the base 64 string from this id and return that
     *
     * @param id
     * @return
     */
    private String convertToShortUrl(long id) {
        StringBuilder sb = new StringBuilder();
        int base = allowedChars.length;
        if (id == 0) {
            return String.valueOf(allowedChars[0]);

        }
        while (id > 0) {
            sb.append(allowedChars[(int) id % base]);
            id = id / base;
        }
        return sb.reverse().toString();

    }

    /**
     * Now first get the decimal from this string , base 64 to decimal The get the
     * original url from DB
     *
     * @param shortUrl
     * @return
     */
    public String getOriginalUrl(String shortUrl) {
        long id = convertBase64ToDecimal(shortUrl);
        Optional<Url> url = this.urlRepository.findById(id);
        Url urlFromDb = url.get();
        if (urlFromDb == null) {
            try {
                throw new CinemaGruvhException("No id exists DB for this ID");
            } catch (CinemaGruvhException e) {
                e.printStackTrace();
            }

        }
        return urlFromDb.getOriginalUrl();
    }

    private long convertBase64ToDecimal(String shortUrl) {
        int base = allowedChars.length;
        int pow = 0;
        int start = shortUrl.length() - 1;
        long decimal = 0;
        while (start >= 0) {
            decimal += allowedCharString.indexOf(shortUrl.charAt(start)) * Math.pow(base, pow++);
            start--;
        }
        return decimal;
    }

}
