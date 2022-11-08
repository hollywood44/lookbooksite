package com.lbs.lookbooksite.repository;

import com.lbs.lookbooksite.domain.StyleTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.beans.JavaBean;

public interface StyleTagRepository extends JpaRepository<StyleTag,String> {
}
