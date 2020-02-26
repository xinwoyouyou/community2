package life.majiang.community.cache;

import life.majiang.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 作者:悠悠我心
 * Tag:标签
 * Cache:缓存
 * program:程序
 * framework:框架
 * server:服务器
 */
public class TagCache {
    public static List<TagDTO> get() {
        final List<TagDTO> tagDTOS = new ArrayList<>();
        final TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList
                ("JS", "PHP", "CSS", "JAVA", "HTML", "python", "VisualBasic.NET", "C++", "C", "javascript", "SQL", "Go"));
        tagDTOS.add(program);

        final TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("laravel ", "spring", "express", "django", "flask", "yii", "uby-on-rails", "tornado", "koa s", "truts "));
        tagDTOS.add(framework);

        final TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux", "nginx ", "docker ", "apache", "ubuntu ", "centos", "缓存", "tomcat", "负载均衡 ", "unix", "hadoop", "windows-server "));
        tagDTOS.add(server);

        final TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql", "redis", "mongodb", "sql ", "oracle", "nosql", "memcached", "sqlserver", "postgresql ", "sqlite "));
        tagDTOS.add(db);

        return tagDTOS;
    }

    /* filter:过滤器
       Invalid:无效
       */
    public static Boolean filterInvalid(String tags) {
        boolean flag = true;
        final String[] tag = StringUtils.split(tags, ",");
        final List<String> tag1 = Arrays.asList(tag);

        final ArrayList<String> tags3 = new ArrayList<>();

        final List<TagDTO> tagDTOs = get();
        for (TagDTO tagDTO : tagDTOs) {
            final List<String> tags2 = tagDTO.getTags();
            tags3.addAll(tags2);
        }
        final boolean b = tags3.containsAll(tag1);
        return !b;

        /*final List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        final String invalid = Arrays.stream(tag).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;*/
    }
}
