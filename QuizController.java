import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private List<Question> questions = new ArrayList<>();

    public QuizController() {
        // Initialize sample quiz questions
        questions.add(new Question("Mikä on Suomen pääkaupunki?", "Helsinki"));
        questions.add(new Question("Mikä on Jaska Jokusen koiran nimi?", "Ressu"));
        questions.add(new Question("Mikä on Karvinen sarjakuvassa olevan kissan lempi ruoka?", "Lasagne"));
    }

    @GetMapping("/")
    public ResponseEntity<String> getInfo() {
        String info = "Tervetuloa visailuun.\n" +
                      "GET /quiz/questions - Saat kaikki kysymykset\n" +
                      "GET /quiz/question/{id} - Saat tietyn kysymyksen sen id-numerolla\n" +
                      "POST /quiz/answer - Lähetä vastaus kysymykseen";
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping("/question/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable int id) {
        if (id >= 0 && id < questions.size()) {
            return new ResponseEntity<>(questions.get(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/answer")
    public ResponseEntity<String> submitAnswer(@RequestParam int questionId, @RequestParam String answer) {
        if (questionId >= 0 && questionId < questions.size()) {
            Question question = questions.get(questionId);
            if (question.getCorrectAnswer().equalsIgnoreCase(answer)) {
                return new ResponseEntity<>("Oikea vastaus!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Väärin. Oikea vastaus olisi: " + question.getCorrectAnswer(), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Väärä kysymyksen id", HttpStatus.BAD_REQUEST);
        }
    }
}
