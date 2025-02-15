package letscode.sarafan.controller;

import letscode.sarafan.exeptions.NotFindException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping ("message")
public class MessageController {
    private int counters = 4;
    public List<Map<String, String>> messages = new ArrayList<Map<String, String>>(){{
        add(new HashMap<String, String>(){{put("id", "1"); put("text", "First message");}});
        add(new HashMap<String, String>(){{put("id", "2"); put("text", "Second message");}});
        add(new HashMap<String, String>(){{put("id", "3"); put("text", "Third message");}});
    }};
    @GetMapping
    public List<Map<String, String>> list(){
        return messages;
    }
    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id){
        return getMessage(id);
    }

    private Map<String, String> getMessage(@PathVariable String id) {
        return messages.stream().filter(massage -> massage.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFindException::new);
    }

    @PostMapping
    public Map<String,String> create (@RequestBody Map<String,String> message){
        message.put("id", String.valueOf(counters++));
        messages.add(message);
        return message;
    }
    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message){
        Map<String, String> messageFromBd = getMessage(id);
        messageFromBd.putAll(message);
        messageFromBd.put("id", id);
        return messageFromBd;
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Map<String, String> message = getMessage(id);
        messages.add(message);
    }
}
